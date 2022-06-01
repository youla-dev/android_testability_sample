package com.example.di

import com.example.common.SchedulersFactory
import com.example.utils.TestIdleSchedulersFactory
import dagger.Binds
import dagger.Module

@Module
abstract class TestSchedulersFactoryModule {
    @Binds
    abstract fun schedulersFactory(schedulersFactory: TestIdleSchedulersFactory): SchedulersFactory
}