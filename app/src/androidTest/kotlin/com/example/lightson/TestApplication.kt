package com.example.lightson

import android.app.Application
import com.example.di.*
import com.example.domain.repository.TrafficLightRepository
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class TestApplication :  Application(), HasAndroidInjector {

    val testAppComponent: TestAppComponent
        get() = _appComponent

    private lateinit var appInjector: AppInjector
    private lateinit var _appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        reCreateComponent()
    }

    override fun androidInjector(): AndroidInjector<Any> = appInjector.androidInjector

    fun reCreateComponent(
        override: TrafficLightRepository? = null
    ) : TestAppComponent {
        _appComponent = buildAppComponent(override)
        appInjector = AppInjector(_appComponent, this)
        return _appComponent
    }

    private fun buildAppComponent(
        override: TrafficLightRepository? = null
    ): TestAppComponent {
        return DaggerTestAppComponent
            .builder()
            .application(this)
            .repoModule(RepoModule(override))
            .build()
    }



}