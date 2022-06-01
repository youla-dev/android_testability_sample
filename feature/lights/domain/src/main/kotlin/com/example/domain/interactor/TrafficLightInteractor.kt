package com.example.domain.interactor

import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository
import com.example.domain.strategy.TrafficLightStrategy
import javax.inject.Inject

/*
 * Personally, I DO prefer Reactive Flows everywhere because of Android OS has a lot of async work.
 * You can use whatever you like - RxJava, Jetbrains Flows, etc.
 *
 * It makes sense to design domain layer as simple as possible, which therefore
 * brings us to using blocking (synchronous) interface.
 * So, you could reuse domain logic in another project/platform/etc. and you won't
 * depend on particular async framework (like Flow or Rx).
 *
 * This logic is not a subject to change, so interactor is not an interface
 */
class TrafficLightInteractor @Inject constructor(
    private val strategy: TrafficLightStrategy,
    private val repository: TrafficLightRepository
) {

    /**
     * Strictly init with submitted light
     */
    fun init(state: TrafficFeatureState): TrafficFeatureState {
        repository.saveState(state)
        return state
    }

    /**
     * Get last saved from data layer and init
     */
    fun initWithLastSaved(): TrafficFeatureState {
        return repository.restoreState() ?: throw IllegalStateException("could not restore state")
    }

    /**
     * Find out what is the next state.
     */
    private fun next(currentState: TrafficFeatureState): TrafficFeatureState {
        val twoStepsBeforeLight = currentState.previous
        val currentLight = currentState.current
        val nextLight = strategy.nextLight(twoStepsBeforeLight, currentLight)
        return TrafficFeatureState(previous = currentLight, current = nextLight)
    }

    fun nextState(): TrafficFeatureState {
        val lastState =
            repository.restoreState() ?: throw IllegalStateException("could not restore state")
        val next = next(lastState)
        repository.saveState(next)
        return next
    }

}