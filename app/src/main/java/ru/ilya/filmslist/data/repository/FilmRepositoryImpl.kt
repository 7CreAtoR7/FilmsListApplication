package ru.ilya.filmslist.data.repository

import androidx.lifecycle.LiveData
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository

class FilmRepositoryImpl: FilmRepository {
    override fun getAllFilmsListFromDb(): LiveData<List<FilmItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun addFilmsListToDb(filmsList: List<FilmItem>) {
        TODO("Not yet implemented")
    }

    override fun getFavouriteFilmsFromDb(): LiveData<List<FilmItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFilmFavouriteStatus(filmItem: FilmItem) {
        TODO("Not yet implemented")
    }

}