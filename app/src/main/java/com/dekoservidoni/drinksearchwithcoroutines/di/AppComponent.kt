package com.dekoservidoni.drinksearchwithcoroutines.di

import com.dekoservidoni.drinksearchwithcoroutines.BaseApp
import com.dekoservidoni.drinksearchwithcoroutines.di.modules.ActivityBuildersModule
import com.dekoservidoni.drinksearchwithcoroutines.di.modules.GlobalModule
import com.dekoservidoni.drinksearchwithcoroutines.di.modules.NetworkModule
import com.dekoservidoni.drinksearchwithcoroutines.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    GlobalModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    ActivityBuildersModule::class])
interface AppComponent: AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApp): AppComponent.Builder
        fun build(): AppComponent
    }

    override fun inject(application: BaseApp)
}