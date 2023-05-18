package ru.ilya.filmslist.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ilya.filmslist.util.Constants


class ApiFactory {

    companion object {
        private var INSTANCE: ApiService? = null
        private val LOCK = Any()


        fun getApiService(): ApiService {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                INSTANCE = retrofit.create(ApiService::class.java)
                return retrofit.create(ApiService::class.java)
            }
        }
    }
}