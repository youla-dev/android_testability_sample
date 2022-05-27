package com.example.lightson

import android.app.Application
import com.example.di.AppComponent
import com.example.di.AppInjector
import com.example.di.DaggerTestAppComponent
import com.example.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class TestApplication :  Application(), HasAndroidInjector {

    @Suppress("CAST_NEVER_SUCCEEDS")
    val testAppComponent: DaggerTestAppComponent
        get() = _appComponent as DaggerTestAppComponent

    private lateinit var appInjector: AppInjector
    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        reCreateComponent()
    }

    override fun androidInjector(): AndroidInjector<Any> = appInjector.androidInjector

    fun reCreateComponent(override: Any? = null) {
        _appComponent = buildAppComponent(override)
        appInjector = AppInjector(_appComponent, this)
    }

    private fun buildAppComponent(override: Any? = null): TestAppComponent {
        return DaggerTestAppComponent
            .builder()
            .application(this)
            .build()
    }



}