package ru.ilya.filmslist.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("accept: application/json", "X-API-KEY: 71f6d356-ad50-42ff-a336-3b8fa804af09")
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(
        @Query("page") page: Int
    ): ResponseFilmsDTO

    @Headers("accept: application/json", "X-API-KEY: 71f6d356-ad50-42ff-a336-3b8fa804af09")
    @GET("api/v2.2/films/{id}")
    suspend fun getDetailInfo(@Path("id") id: Long): DetailedFilmDTO

}
