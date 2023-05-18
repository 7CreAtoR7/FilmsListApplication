package ru.ilya.filmslist.domain.usecases.dbUseCases

import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository
import javax.inject.Inject

class UpdateFilmFavouriteStatusUseCase @Inject constructor(
    private val repository: FilmRepository
) {

    suspend fun updateFilmFavouriteStatusInDb(filmItem: FilmItem) {
        return repository.updateFilmFavouriteStatus(filmItem)
    }
}