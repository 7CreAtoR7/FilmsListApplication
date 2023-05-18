package ru.ilya.filmslist.presentation.ui.favouriteScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ilya.filmslist.databinding.ActivityFavouriteBinding
import ru.ilya.filmslist.presentation.FilmApplication
import ru.ilya.filmslist.presentation.ViewModelFactory
import ru.ilya.filmslist.presentation.adapter.FilmsAdapter
import javax.inject.Inject

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var adapterFilms: FilmsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as FilmApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]

        setupRecyclerView()
        setUpClickListeners()
        markButtonDisable()
        initObservers()
    }

    private fun setupRecyclerView() {
        with(binding.rvFilms) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterFilms = FilmsAdapter()
            adapter = adapterFilms

            recycledViewPool.setMaxRecycledViews(
                FilmsAdapter.VIEW_TYPE_WITHOUT_STAR,
                FilmsAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                FilmsAdapter.VIEW_TYPE_STAR,
                FilmsAdapter.MAX_POOL_SIZE
            )
        }
    }

    private fun setUpClickListeners() {
        binding.popularBtn.setOnClickListener {
            finish()
        }
    }

    private fun markButtonDisable() {
        binding.popularBtn.isEnabled = true
        binding.favouriteBtn.isEnabled = false
    }

    private fun initObservers() {
        viewModel.favouriteFilmsList.observe(this) {
            adapterFilms.submitList(it)
        }
    }

    companion object {

        fun newIntentFavourite(context: Context): Intent {
            return Intent(context, FavouriteActivity::class.java)
        }
    }

}