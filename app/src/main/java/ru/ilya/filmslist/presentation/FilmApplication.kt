package ru.ilya.filmslist.presentation

import android.app.Application
import ru.ilya.filmslist.di.DaggerApplicationComponent

class FilmApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}