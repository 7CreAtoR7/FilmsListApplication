package ru.ilya.filmslist.presentation.ui.favouriteScreen

import androidx.lifecycle.ViewModel
import ru.ilya.filmslist.domain.usecases.dbUseCases.GetFavouriteFilmsFromDbUseCase
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val getFavouriteFilmsFromDbUseCase: GetFavouriteFilmsFromDbUseCase
) : ViewModel() {

    val favouriteFilmsList = getFavouriteFilmsFromDbUseCase.getFavouriteFilmsFromDb()

}