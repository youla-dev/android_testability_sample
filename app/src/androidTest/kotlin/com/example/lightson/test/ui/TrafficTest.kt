package com.example.lightson.test.ui

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.example.lightson.TestApplication
import com.example.lightson.TrafficLightActivity
import com.example.lightson.screen.TrafficScreen
import com.example.lightson.test.base.BaseTestCase
import org.junit.Test

class TrafficTest: BaseTestCase() {

    override fun inject() {
        /*ApplicationProvider
            .getApplicationContext<TestApplication>()
            .testAppComponent
            .inject(this)*/
    }

    @Test
    fun testInit() {
        before {
            val intent = Intent(ApplicationProvider.getApplicationContext<TestApplication>(), TrafficLightActivity::class.java)
            val activityScenario = launchActivity<TrafficLightActivity>(intent)
            activityScenario.moveToState(Lifecycle.State.RESUMED)
        }.after {  }
            .run {
                TrafficScreen{
                    button.containsText("green")
                }
            }
    }

}