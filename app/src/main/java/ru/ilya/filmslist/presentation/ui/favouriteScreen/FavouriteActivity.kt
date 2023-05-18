package ru.ilya.filmslist.presentation.ui.favouriteScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ilya.filmslist.databinding.ActivityFavouriteBinding
import ru.ilya.filmslist.presentation.adapter.FilmsAdapter

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var adapterFilms: FilmsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

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