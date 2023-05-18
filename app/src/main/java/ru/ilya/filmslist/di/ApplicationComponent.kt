package ru.ilya.filmslist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.ilya.filmslist.presentation.ui.detailedScreen.DetailedFragment
import ru.ilya.filmslist.presentation.ui.favouriteScreen.FavouriteActivity
import ru.ilya.filmslist.presentation.ui.filmsScreen.MainActivity

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: DetailedFragment)

    fun inject(activity: FavouriteActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}