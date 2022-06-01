package com.example.di

import com.example.common.SchedulersFactory
import com.example.common.SchedulersFactoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class SchedulersFactoryModule {
    @Binds
    abstract fun schedulersFactory(schedulersFactory: SchedulersFactoryImpl): SchedulersFactory
}
