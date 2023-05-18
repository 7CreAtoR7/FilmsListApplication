package ru.ilya.filmslist.domain.usecases.apiUseCases

import retrofit2.HttpException
import ru.ilya.filmslist.domain.models.FilmItem
import ru.ilya.filmslist.domain.models.ResponseState
import ru.ilya.filmslist.domain.repository.FilmRepository
import java.io.IOException
import javax.inject.Inject

class GetTopFilmsFromApiUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {
    suspend operator fun invoke(
        page: Int
    ): ResponseState<List<FilmItem>> {
        return try {
            val filmsList = filmRepository.getTopFilms(page = page)
            ResponseState.Success<List<FilmItem>>(filmsList)
        } catch (e: HttpException) {
            ResponseState.Error<List<FilmItem>>(e.localizedMessage ?: "Неожиданная ошибка")
        } catch (e: IOException) {
            ResponseState.Error<List<FilmItem>>("Проблемы с интернет соединением")
        }
    }
}