package ru.ilya.filmslist.domain.repository

import androidx.lifecycle.LiveData
import ru.ilya.filmslist.domain.models.DetailedFilmItem
import ru.ilya.filmslist.domain.models.FilmItem

interface FilmRepository {

    fun getAllFilmsListFromDb(): LiveData<List<FilmItem>>

    suspend fun addFilmsListToDb(filmsList: List<FilmItem>)

    fun getFavouriteFilmsFromDb(): LiveData<List<FilmItem>>

    suspend fun updateFilmFavouriteStatus(filmItem: FilmItem)

    suspend fun getTopFilms(page: Int): List<FilmItem>

    suspend fun getDetailInfo(id: Long): DetailedFilmItem

}