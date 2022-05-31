package com.example.di

import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        CommonModule::class,
        TrafficActivityModule::class,
        RepoModule::class
    ]
)
class AppCompositeModule