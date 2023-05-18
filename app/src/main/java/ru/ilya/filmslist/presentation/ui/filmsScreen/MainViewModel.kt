package ru.ilya.filmslist.presentation.ui.filmsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ilya.filmslist.data.network.ApiFactory
import ru.ilya.filmslist.data.repository.FilmRepositoryImpl
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.models.ResponseState
import ru.ilya.filmslist.domain.usecases.apiUseCases.GetTopFilmsFromApiUseCase
import ru.ilya.filmslist.domain.usecases.dbUseCases.GetAllFilmsListFromDbUseCase
import ru.ilya.filmslist.domain.usecases.dbUseCases.UpdateFilmFavouriteStatusUseCase

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        FilmRepositoryImpl(application = application, ApiFactory.getApiService())

    private val getTopFilmsFromApiUseCase = GetTopFilmsFromApiUseCase(repository)
    private val updateFilmFavouriteStatusUseCase = UpdateFilmFavouriteStatusUseCase(repository)
    private val getAllFilmsListFromDbUseCase = GetAllFilmsListFromDbUseCase(repository)

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