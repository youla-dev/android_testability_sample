package com.example.lightson.test.integraion

import androidx.test.core.app.ApplicationProvider
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository
import com.example.lightson.TestApplication
import com.example.lightson.test.base.BaseTestCase
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

class TrafficRepositoryTest: BaseTestCase() {

    override fun inject() {
        ApplicationProvider
            .getApplicationContext<TestApplication>()
            .testAppComponent
            .inject(this)
    }

    @Inject
    lateinit var trafficRepository: TrafficLightRepository

    @Test
    fun testSavingState() {
        val expectedState = TrafficFeatureState(TrafficLight.YELLOW, TrafficLight.GREEN)
        trafficRepository.saveState(expectedState)
        val restoredState = trafficRepository.restoreState()

        Assert.assertEquals(expectedState, restoredState)
    }

}