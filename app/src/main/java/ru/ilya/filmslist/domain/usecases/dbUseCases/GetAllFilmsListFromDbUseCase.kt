package ru.ilya.filmslist.domain.usecases.dbUseCases

import androidx.lifecycle.LiveData
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository

class GetAllFilmsListFromDbUseCase(
    private val repository: FilmRepository
) {

    fun getAllFilmsListFromDb(): LiveData<List<FilmItem>> {
        return repository.getAllFilmsListFromDb()
    }

}