package com.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.DispatchingAndroidInjector
import javax.inject.Singleton

@Component(modules = [
    AppCompositeModule::class
])
@Singleton
interface AppComponent {

    val dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}