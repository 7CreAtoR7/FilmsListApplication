package ru.ilya.filmslist.presentation.ui.detailedScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ilya.filmslist.data.network.ApiFactory
import ru.ilya.filmslist.data.repository.FilmRepositoryImpl
import ru.ilya.filmslist.domain.models.DetailedFilmItem
import ru.ilya.filmslist.domain.models.ResponseState
import ru.ilya.filmslist.domain.usecases.apiUseCases.GetDetailInfoFromApiUseCase

class DetailedViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        FilmRepositoryImpl(application = application, ApiFactory.getApiService())

    private val getDetailInfoFromApiUseCase = GetDetailInfoFromApiUseCase(repository)

    private val _stateDetailFilm = MutableLiveData<DetailedFilmState>()
    val stateDetailFilm: LiveData<DetailedFilmState> = _stateDetailFilm

    fun getDetailFilmFromApi(id: Long) {
        viewModelScope.launch {
            _stateDetailFilm.value = DetailedFilmState.Loading
            when (val detailedFilmItem: ResponseState<DetailedFilmItem> =
                getDetailInfoFromApiUseCase.invoke(filmId = id)) {
                is ResponseState.Success -> {
                    _stateDetailFilm.value =
                        DetailedFilmState.Success(detailedFilmItem = detailedFilmItem.data!!)
                }
                is ResponseState.Loading -> {
                    _stateDetailFilm.value = DetailedFilmState.Loading
                }
                is ResponseState.Error -> {
                    _stateDetailFilm.value = DetailedFilmState.Error(
                        message = detailedFilmItem.message ?: "An Unexpected Error"
                    )
                }
            }
        }
    }

}