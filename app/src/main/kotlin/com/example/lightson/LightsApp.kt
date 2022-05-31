package com.example.lightson

import android.app.Application
import com.example.di.AppComponent
import com.example.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import com.example.di.DaggerAppComponent;

class LightsApp : Application(), HasAndroidInjector {

    private lateinit var appInjector: AppInjector

    override fun onCreate() {
        super.onCreate()
        val appComponent = buildAppComponent()
        appInjector = AppInjector(appComponent, this)
    }

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .application(this)
            //.repoModule(RepoModule())
            .build()
    }

    override fun androidInjector(): AndroidInjector<Any> = appInjector.androidInjector

}