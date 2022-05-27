package com.example.di

import android.app.Application
import com.example.lightson.test.traffic.TrafficTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppCompositeModule::class
])
@Singleton
interface TestAppComponent : AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent

    }

    fun inject(test: TrafficTest)

}