package com.dekoservidoni.drinksearchwithcoroutines.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dekoservidoni.drinksearchwithcoroutines.di.ViewModelFactory
import com.dekoservidoni.drinksearchwithcoroutines.di.ViewModelKey
import com.dekoservidoni.drinksearchwithcoroutines.view.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}