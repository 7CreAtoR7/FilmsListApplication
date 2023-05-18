package ru.ilya.filmslist.presentation.ui.favouriteScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.ilya.filmslist.data.network.ApiFactory
import ru.ilya.filmslist.data.repository.FilmRepositoryImpl
import ru.ilya.filmslist.domain.usecases.dbUseCases.GetFavouriteFilmsFromDbUseCase

class FavouriteViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        FilmRepositoryImpl(application = application, ApiFactory.getApiService())

    private val getFavouriteFilmsFromDbUseCase = GetFavouriteFilmsFromDbUseCase(repository)

    val favouriteFilmsList = getFavouriteFilmsFromDbUseCase.getFavouriteFilmsFromDb()

}