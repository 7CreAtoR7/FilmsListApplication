package ru.ilya.filmslist.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.ilya.filmslist.data.database.FilmsDao
import ru.ilya.filmslist.data.database.FilmsDatabase
import ru.ilya.filmslist.data.repository.FilmRepositoryImpl
import ru.ilya.filmslist.domain.repository.FilmRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindFilmRepository(impl: FilmRepositoryImpl): FilmRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideFilmsDao(
            application: Application
        ): FilmsDao {
            return FilmsDatabase.getInstance(application).filmsDao()
        }
    }
}