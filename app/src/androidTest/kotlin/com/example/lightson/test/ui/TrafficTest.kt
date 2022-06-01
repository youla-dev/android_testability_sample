package com.example.lightson.test.ui

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.example.contract.domain.ResourceRepository
import com.example.lightson.R
import com.example.lightson.TestApplication
import com.example.lightson.TrafficLightActivity
import com.example.lightson.screen.TrafficScreen
import com.example.lightson.test.base.BaseTestCase
import org.junit.Test
import javax.inject.Inject

class TrafficTest: BaseTestCase() {

    /*
     * This is almost e2e test.
     * There is an assumption about `Initial Green light` in the tests,
     * so you might want to substitute repository (or smth), to ensure never changing starting point
     */

    override fun inject() {
        ApplicationProvider
            .getApplicationContext<TestApplication>()
            .testAppComponent
            .inject(this)
    }

    @Inject
    lateinit var resourceRepository: ResourceRepository

    @Test
    fun testInit() {
        before {
            TrafficScreen.launch()
        }.after {  }
            .run {
                val expectedPart: String = resourceRepository.getString(R.string.attention)
                val buttonPart: String = resourceRepository.getString(R.string.button_text)
                val expectedText: String = String.format(buttonPart, expectedPart)

                TrafficScreen{
                    progress.isNotDisplayed()
                    button.containsText(expectedText)
                }
            }
    }

    @Test
    fun testClick() {
        before {
            TrafficScreen.launch()
        }.after {  }
            .run {
                TrafficScreen {
                    progress.isNotDisplayed()
                    button.isDisplayed()
                }
                TrafficScreen {
                    button.click()
                }
                TrafficScreen {
                    progress.isDisplayed()
                }

                val expectedPart: String = resourceRepository.getString(R.string.stop)
                val buttonPart: String = resourceRepository.getString(R.string.button_text)
                val expectedText: String = String.format(buttonPart, expectedPart)
                TrafficScreen {
                    //flakySafely {
                        progress.isNotDisplayed()
                    //}
                    button.containsText(expectedText)
                }
            }
    }

}