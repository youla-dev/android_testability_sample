package com.example.lightson.test.integraion

import androidx.test.core.app.ApplicationProvider
import com.example.domain.interactor.TrafficLightInteractor
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository
import com.example.lightson.TestApplication
import com.example.lightson.test.base.BaseTestCase
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

/*
    This test is merely a demonstration of avoiding unnecessary codegen with DaggerAndroid.
    As a rule of thumb you want to write unit test for interactors aka business logic
 */
class TrafficInteractorIntegrationTest : BaseTestCase() {

    override fun inject() {
        val repo = object : TrafficLightRepository {
            private val innerState = AtomicReference<TrafficFeatureState>()

            override fun saveState(state: TrafficFeatureState): Boolean {
                innerState.set(state)
                return true
            }

            override fun restoreState(): TrafficFeatureState? {
                return innerState.get()
            }
        }
        ApplicationProvider
            .getApplicationContext<TestApplication>()
            .reCreateComponent(override = repo)
            .inject(this)
    }

    @Inject
    lateinit var trafficLightInteractor: TrafficLightInteractor

    @Test
    fun checkInitialStateCorrect() {
        val state = TrafficFeatureState(TrafficLight.GREEN, TrafficLight.YELLOW)

        val initState = trafficLightInteractor.init(state)
        Assert.assertEquals(state, initState)
    }

    @Test
    fun checkNextStateIsCorrect() {
        val state = TrafficFeatureState(TrafficLight.GREEN, TrafficLight.YELLOW)
        val expectedNextState = TrafficFeatureState(TrafficLight.YELLOW, TrafficLight.RED)

        trafficLightInteractor.init(state)
        val nextState = trafficLightInteractor.nextState()
        Assert.assertEquals(expectedNextState, nextState)
    }

}