package ru.ilya.filmslist.domain.usecases.apiUseCases

import retrofit2.HttpException
import ru.ilya.filmslist.domain.models.DetailedFilmItem
import ru.ilya.filmslist.domain.models.ResponseState
import ru.ilya.filmslist.domain.repository.FilmRepository
import java.io.IOException

class GetDetailInfoFromApiUseCase(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(
        filmId: Long
    ): ResponseState<DetailedFilmItem> {
        return try {
            val detailedFilmItem = filmRepository.getDetailInfo(id = filmId)
            ResponseState.Success<DetailedFilmItem>(detailedFilmItem)
        } catch (e: HttpException) {
            ResponseState.Error<DetailedFilmItem>(e.localizedMessage ?: "Неожиданная ошибка")
        } catch (e: IOException) {
            ResponseState.Error<DetailedFilmItem>("Проблемы с интернет соединением")
        }
    }
}