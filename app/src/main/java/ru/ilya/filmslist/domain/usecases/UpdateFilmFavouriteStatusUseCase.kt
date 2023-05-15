package ru.ilya.filmslist.domain.usecases

import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository

class UpdateFilmFavouriteStatusUseCase(
    private val repository: FilmRepository
) {

    suspend fun getFavouriteFilmsFromDb(filmItem: FilmItem) {
        return repository.updateFilmFavouriteStatus(filmItem)
    }

}