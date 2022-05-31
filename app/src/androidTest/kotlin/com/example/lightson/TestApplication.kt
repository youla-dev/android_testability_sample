package com.example.lightson

import android.app.Application
import com.example.di.AppComponent
import com.example.di.AppInjector
import com.example.di.DaggerTestAppComponent
import com.example.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import org.junit.Test

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

    fun reCreateComponent(override: Any? = null) : TestAppComponent {
        _appComponent = buildAppComponent(override)
        appInjector = AppInjector(_appComponent, this)
        return _appComponent
    }

    private fun buildAppComponent(override: Any? = null): TestAppComponent {
        return DaggerTestAppComponent
            .builder()
            .application(this)
            .build()
    }



}