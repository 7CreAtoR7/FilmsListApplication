package ru.ilya.filmslist.presentation.ui.filmsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.models.ResponseState
import ru.ilya.filmslist.domain.usecases.apiUseCases.GetTopFilmsFromApiUseCase
import ru.ilya.filmslist.domain.usecases.dbUseCases.GetAllFilmsListFromDbUseCase
import ru.ilya.filmslist.domain.usecases.dbUseCases.UpdateFilmFavouriteStatusUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTopFilmsFromApiUseCase: GetTopFilmsFromApiUseCase,
    private val updateFilmFavouriteStatusUseCase: UpdateFilmFavouriteStatusUseCase,
    private val getAllFilmsListFromDbUseCase: GetAllFilmsListFromDbUseCase
) : ViewModel() {

    val filmsList = getAllFilmsListFromDbUseCase.getAllFilmsListFromDb()

    private val _stateTopFilms = MutableLiveData<FilmsListState>()
    val stateTopFilms: LiveData<FilmsListState> = _stateTopFilms

    fun getTopFilmsFromApi(page: Int) {
        viewModelScope.launch {
            _stateTopFilms.value = FilmsListState.Loading
            when (val filmsList: ResponseState<List<FilmItem>> =
                getTopFilmsFromApiUseCase.invoke(page = page)) {
                is ResponseState.Success -> {
                    _stateTopFilms.value =
                        FilmsListState.Success(filmsList = filmsList.data ?: emptyList())
                }
                is ResponseState.Loading -> {
                    _stateTopFilms.value = FilmsListState.Loading
                }
                is ResponseState.Error -> {
                    _stateTopFilms.value = FilmsListState.Error(
                        message = filmsList.message ?: "An Unexpected Error"
                    )
                }
            }
        }
    }

    fun changeEnableState(filmItem: FilmItem) { // изменение только статуса
        viewModelScope.launch {
            val newFilmItem = filmItem.copy(isFavourite = !filmItem.isFavourite)
            updateFilmFavouriteStatusUseCase.updateFilmFavouriteStatusInDb(newFilmItem)
        }
    }
}