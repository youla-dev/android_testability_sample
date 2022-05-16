package com.example.di

import com.example.lightson.TrafficLightActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrafficActivityModule() {

    @ContributesAndroidInjector
    abstract fun contributeTrafficLightActivity() : TrafficLightActivity

}