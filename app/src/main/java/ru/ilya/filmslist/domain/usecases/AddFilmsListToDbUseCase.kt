package ru.ilya.filmslist.domain.usecases

import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository

class AddFilmsListToDbUseCase(
    private val repository: FilmRepository
) {

    suspend fun addFilmsListToDb(filmsList: List<FilmItem>) {
        return repository.addFilmsListToDb(filmsList)
    }

}