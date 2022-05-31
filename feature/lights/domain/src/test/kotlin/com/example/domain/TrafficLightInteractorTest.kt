package com.example.domain

import com.example.domain.interactor.TrafficLightInteractor
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository
import com.example.domain.strategy.TrafficLightStrategy
import com.example.domain.strategy.TrafficLightStrategyImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

/*
 *  It's never too late to write a good unit test =)
 *  We also could write an integration test like [TrafficInteractorIntegrationTest], if we wanted to
 */
class TrafficLightInteractorTest {

    private val strategy: TrafficLightStrategy = TrafficLightStrategyImpl()
    private lateinit var repo: TrafficLightRepository

    @Before
    fun setupRepo() {
        repo = object : TrafficLightRepository {
            private val innerState = AtomicReference<TrafficFeatureState>()

            override fun saveState(state: TrafficFeatureState): Boolean {
                innerState.set(state)
                return true
            }

            override fun restoreState(): TrafficFeatureState? {
                return innerState.get()
            }
        }
    }

    @Test
    fun checkInitialStateCorrect() {
        val state = TrafficFeatureState(TrafficLight.GREEN, TrafficLight.YELLOW)

        val interactor = TrafficLightInteractor(strategy, repo)

        val initState = interactor.init(state)
        Assert.assertEquals(state, initState)
    }

    @Test
    fun checkNextStateIsCorrect() {
        val state = TrafficFeatureState(TrafficLight.GREEN, TrafficLight.YELLOW)
        val expectedNextState = TrafficFeatureState(TrafficLight.YELLOW, TrafficLight.RED)

        val interactor = TrafficLightInteractor(strategy, repo)

        interactor.init(state)
        val nextState = interactor.nextState()
        Assert.assertEquals(expectedNextState, nextState)
    }

    //rest checks are the homework for a reader

}