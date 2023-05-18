package ru.ilya.filmslist.presentation.ui.detailedScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ilya.filmslist.domain.models.DetailedFilmItem
import ru.ilya.filmslist.domain.models.ResponseState
import ru.ilya.filmslist.domain.usecases.apiUseCases.GetDetailInfoFromApiUseCase
import javax.inject.Inject

class DetailedViewModel @Inject constructor(
    private val getDetailInfoFromApiUseCase: GetDetailInfoFromApiUseCase
) : ViewModel() {

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