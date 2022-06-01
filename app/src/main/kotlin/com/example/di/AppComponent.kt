package com.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.DispatchingAndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AppCompositeModule::class,
        //this way you'll have more modules - a little bit more codegen
        SchedulersFactoryModule::class
    ]
)
@Singleton
interface AppComponent {

    val dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        //for override example
        fun repoModule(repoModule: RepoModule): Builder

        fun build(): AppComponent

    }

}