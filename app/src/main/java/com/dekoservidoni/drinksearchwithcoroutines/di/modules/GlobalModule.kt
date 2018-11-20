package com.dekoservidoni.drinksearchwithcoroutines.di.modules

import android.content.Context
import com.dekoservidoni.drinksearchwithcoroutines.BaseApp
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GlobalModule {

    @Provides
    fun provideContext(application: BaseApp)
            : Context = application.applicationContext

    @Singleton
    @Provides
    fun provideGson()
            : Gson = GsonBuilder().create()
}