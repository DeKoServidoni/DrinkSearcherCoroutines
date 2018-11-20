package com.dekoservidoni.drinksearchwithcoroutines.di.modules

import com.dekoservidoni.drinksearchwithcoroutines.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity
}