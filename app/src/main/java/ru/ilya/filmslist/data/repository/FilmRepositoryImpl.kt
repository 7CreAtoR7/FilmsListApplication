package ru.ilya.filmslist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.ilya.filmslist.data.database.FilmsDatabase
import ru.ilya.filmslist.data.mapper.FilmMapper
import ru.ilya.filmslist.data.network.ApiService
import ru.ilya.filmslist.domain.models.DetailedFilmItem
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository

class FilmRepositoryImpl(
    application: Application,
    private val api: ApiService
) : FilmRepository {

    private val filmsListDao = FilmsDatabase.getInstance(application).filmsDao()
    private val mapper = FilmMapper()

    override fun getAllFilmsListFromDb(): LiveData<List<FilmItem>> {
        return Transformations.map(filmsListDao.getAllFilmsListFromDb()) {
            mapper.mapListFilmItemDBModelToListFilmItem(it)
        }
    }

    override suspend fun addFilmsListToDb(filmsList: List<FilmItem>) {
        filmsListDao.addFilmsListToDb(mapper.mapListFilmItemToListFilmItemDBModel(filmsList))
    }

    override fun getFavouriteFilmsFromDb(): LiveData<List<FilmItem>> {
        return Transformations.map(filmsListDao.getFavouriteFilmsFromDb()) {
            mapper.mapListFilmItemDBModelToListFilmItem(it)
        }
    }

    override suspend fun updateFilmFavouriteStatus(filmItem: FilmItem) {
        filmsListDao.updateFilmFavouriteStatus(filmItem.isFavourite, filmItem.id)
    }

    override suspend fun getTopFilms(page: Int): List<FilmItem> {
        // как получили фильмы с бека - обновляем бд
        val topFilms = mapper.mapListFilmItemDTOToListFilmItem(api.getTopFilms(page = page).films)
        addFilmsListToDb(topFilms)
        return topFilms
    }

    override suspend fun getDetailInfo(id: Long): DetailedFilmItem {
        return mapper.mapDetailedFilmDTOToDetailedFilmItem(api.getDetailInfo(id = id))
    }


}