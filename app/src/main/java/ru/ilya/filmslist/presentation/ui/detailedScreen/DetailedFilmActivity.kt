package ru.ilya.filmslist.presentation.ui.detailedScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ilya.filmslist.R
import ru.ilya.filmslist.domain.models.FilmItem

class DetailedFilmActivity : AppCompatActivity() {

    private var filmId = FilmItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_film)
        supportActionBar?.hide()

        parseIntent()
        if (savedInstanceState == null) {
            launchDetailedFragment(filmId)
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(FILM_ITEM_ID)) {
            throw RuntimeException("Param FILM_ITEM_ID mode is absent!")
        }
        val parsedFilmId = intent.getLongExtra(FILM_ITEM_ID, 0L)
        filmId = parsedFilmId
    }

    private fun launchDetailedFragment(filmId: Long) {
        val fragment = DetailedFragment.newInstanceDetailedFilmItem(filmId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_detail_container_portrait, fragment)
            .commit()
    }

    companion object {

        private const val FILM_ITEM_ID = "film_item_id"

        fun newIntentDetailedFilmItem(context: Context, filmId: Long): Intent {
            val intent = Intent(context, DetailedFilmActivity::class.java)
            intent.putExtra(FILM_ITEM_ID, filmId)
            return intent
        }
    }
}