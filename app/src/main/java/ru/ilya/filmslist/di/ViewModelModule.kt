package ru.ilya.filmslist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ilya.filmslist.presentation.ui.detailedScreen.DetailedViewModel
import ru.ilya.filmslist.presentation.ui.favouriteScreen.FavouriteViewModel
import ru.ilya.filmslist.presentation.ui.filmsScreen.MainViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    fun bindFavouriteViewModel(viewModel: FavouriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailedViewModel::class)
    fun bindDetailedViewModel(viewModel: DetailedViewModel): ViewModel
}