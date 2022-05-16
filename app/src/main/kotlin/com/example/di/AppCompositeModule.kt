package com.example.di

import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        TrafficActivityModule::class
    ]
)
class AppCompositeModule