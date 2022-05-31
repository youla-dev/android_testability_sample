package com.example.di

import android.content.SharedPreferences
import com.example.data.TrafficLightRepositoryImpl
import com.example.domain.repository.TrafficLightRepository
import com.example.domain.strategy.TrafficLightStrategy
import com.example.domain.strategy.TrafficLightStrategyImpl
import dagger.Module
import dagger.Provides

@Module
class RepoModule(
    private val repoOverride: TrafficLightRepository? = null
) {

    @Provides
    fun provideStrategy(): TrafficLightStrategy {
        return TrafficLightStrategyImpl()
    }

    @Provides
    fun provideRepo(shp: SharedPreferences): TrafficLightRepository {
        if (repoOverride != null)
            return repoOverride

        return TrafficLightRepositoryImpl(shp)
    }

}