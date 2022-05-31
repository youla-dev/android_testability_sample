package com.example.lightson.test.integraion

import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.contract.domain.ResourceRepository
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.lightson.R
import com.example.lightson.TestApplication
import com.example.lightson.mapper.TrafficStateMapper
import com.example.lightson.test.base.BaseTestCase
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

class TrafficStateMapperTest : BaseTestCase() {

    @Inject
    lateinit var resourceRepository: ResourceRepository

    @Inject
    lateinit var trafficStateMapper: TrafficStateMapper

    //private val red = Color.parseColor("#ff0000")
    //private val yellow = Color.parseColor("#ffff00")
    private val green = Color.parseColor("#00ff00")

    override fun inject() {
        ApplicationProvider
            .getApplicationContext<TestApplication>()
            .testAppComponent
            .inject(this)
    }

    @Test
    fun assertGreenLight() {
        //Traffic Light Green should result in green light and `attention` text
        val domain = TrafficFeatureState(previous = null, current = TrafficLight.GREEN)

        val expectedPart: String = resourceRepository.getString(R.string.attention)
        val buttonPart: String = resourceRepository.getString(R.string.button_text)
        val expectedText: String = String.format(buttonPart, expectedPart)

        val testState = trafficStateMapper.map(domain)

        Assert.assertEquals(
            expectedText,
            testState.buttonText
        )
        Assert.assertEquals(green, testState.backgroundColor)
    }

    //the rest is task for a reader =)

}