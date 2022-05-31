package com.example.di

import android.app.Application
import com.example.lightson.test.integraion.TrafficRepositoryTest
import com.example.lightson.test.integraion.TrafficStateMapperTest
import com.example.lightson.test.ui.TrafficTest
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

        fun repoModule(repoModule: RepoModule) : Builder

        fun build(): TestAppComponent

    }

    fun inject(test: TrafficTest)
    fun inject(test: TrafficStateMapperTest)
    fun inject(test: TrafficRepositoryTest)

}