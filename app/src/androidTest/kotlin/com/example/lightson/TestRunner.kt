package com.example.lightson

import android.app.Application
import android.content.Context
import android.os.Build
import com.github.tmurakami.dexopener.DexOpener
import io.qameta.allure.android.runners.AllureAndroidJUnitRunner

/**
 * Needed for allure results format, use any other if you wish other
 */
class TestRunner: AllureAndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            DexOpener.install(this)
        }
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }

}