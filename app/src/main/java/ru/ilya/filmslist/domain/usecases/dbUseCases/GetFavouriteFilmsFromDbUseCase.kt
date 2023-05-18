package ru.ilya.filmslist.domain.usecases.dbUseCases

import androidx.lifecycle.LiveData
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.repository.FilmRepository
import javax.inject.Inject

class GetFavouriteFilmsFromDbUseCase @Inject constructor(
    private val repository: FilmRepository
) {

    fun getFavouriteFilmsFromDb(): LiveData<List<FilmItem>> {
        return repository.getFavouriteFilmsFromDb()
    }
}