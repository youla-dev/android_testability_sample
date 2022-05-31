package com.example.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.common.ResourceRepositoryImpl
import com.example.contract.domain.ResourceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonModule {

    //one could use @Binds, I just did not want to make too many modules for this sample
    @Provides
    @Singleton
    fun provideResourceRepository(app: Application): ResourceRepository {
        return ResourceRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application) : SharedPreferences {
        return app.getSharedPreferences("traffic_light_defaults", Context.MODE_PRIVATE)
    }

}