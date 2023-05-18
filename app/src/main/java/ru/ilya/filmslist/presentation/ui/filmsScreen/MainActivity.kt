package ru.ilya.filmslist.presentation.ui.filmsScreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ilya.filmslist.R
import ru.ilya.filmslist.databinding.ActivityMainBinding
import ru.ilya.filmslist.presentation.FilmApplication
import ru.ilya.filmslist.presentation.ViewModelFactory
import ru.ilya.filmslist.presentation.adapter.FilmsAdapter
import ru.ilya.filmslist.presentation.ui.detailedScreen.DetailedFilmActivity
import ru.ilya.filmslist.presentation.ui.detailedScreen.DetailedFragment
import ru.ilya.filmslist.presentation.ui.favouriteScreen.FavouriteActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var filmsAdapter: FilmsAdapter
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as FilmApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setupRecyclerView()
        initObservers()
        getTopFilmsFromApi()
    }

    private fun getTopFilmsFromApi(page: Int = FIRST_PAGE) {
        viewModel.getTopFilmsFromApi(page)
    }

    private fun initObservers() {
        viewModel.filmsList.observe(this) {
            filmsAdapter.submitList(it)
        }

        viewModel.stateTopFilms.observe(this) { state ->
            when (state) {
                is FilmsListState.Loading -> {
                    binding.progressLoader.visibility = View.VISIBLE
                }
                is FilmsListState.Success -> {
                    binding.progressLoader.visibility = View.GONE
                }
                is FilmsListState.Error -> {
                    binding.progressLoader.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFilms) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            filmsAdapter = FilmsAdapter()
            adapter = filmsAdapter

            recycledViewPool.setMaxRecycledViews(
                FilmsAdapter.VIEW_TYPE_WITHOUT_STAR,
                FilmsAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                FilmsAdapter.VIEW_TYPE_STAR,
                FilmsAdapter.MAX_POOL_SIZE
            )
        }
        // установка слушателей
        setupClickListener()
        setupLongClickListener()
        setUpButtonsClickListeners()
        markButtonDisable()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.filmDetailContainer == null // в landscape filmDetailContainer - второй экран
    }

    private fun setupClickListener() {
        filmsAdapter.onFilmItemClickListener = {
            if (isOnePaneMode()) {
                val intent = DetailedFilmActivity.newIntentDetailedFilmItem(this, it.id)
                startActivity(intent)
            } else {
                // альбомная ориентация
                launchLandScapeFragment(DetailedFragment.newInstanceDetailedFilmItem(it.id))
            }
        }
    }

    private fun launchLandScapeFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_detail_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun markButtonDisable() {
        binding.popularBtn.isEnabled = false
        binding.favouriteBtn.isEnabled = true
    }

    private fun setUpButtonsClickListeners() {
        binding.favouriteBtn.setOnClickListener {
            val intent = FavouriteActivity.newIntentFavourite(this)
            startActivity(intent)
        }
    }


    private fun setupLongClickListener() {
        filmsAdapter.onFilmItemLongClickListener = {
            if (!it.isFavourite) {
                // тостик, что фильм добавлен в избранное (обновляем статус в бд)
                Toast.makeText(this, "Фильм ${it.name} добавлен в избранное!", Toast.LENGTH_SHORT)
                    .show()
                viewModel.changeEnableState(it)
            } else {
                // тостик, что фильм удалён из избранного (обновляем статус в бд)
                Toast.makeText(this, "Фильм ${it.name} удалён из избранного!", Toast.LENGTH_SHORT)
                    .show()
                viewModel.changeEnableState(it)
            }
        }
    }

    companion object {

        private const val FIRST_PAGE = 1
    }
}