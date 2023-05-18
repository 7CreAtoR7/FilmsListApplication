package ru.ilya.filmslist.domain.usecases.dbUseCases

import androidx.lifecycle.LiveData
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository

class GetFavouriteFilmsFromDbUseCase(
    private val repository: FilmRepository
) {

    fun getFavouriteFilmsFromDb(): LiveData<List<FilmItem>> {
        return repository.getFavouriteFilmsFromDb()
    }
}