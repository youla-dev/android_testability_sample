package com.example.domain.interactor

import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository
import com.example.domain.strategy.TrafficLightStrategy
import javax.inject.Inject

/**
 * Personally, I do prefer Reactive Flows for Android OS has so many async work.
 * You can use whatever you like - RxJava, Jetbrains Flows, etc.
 *
 * It makes sense to design domain layer as simple as possible, which therefore
 * brings us to using blocking (synchronous) interface.
 * So, you could reuse domain logic in another project/platform/etc. and you won't
 * depend on particular async framework (like Flow or Rx).
 *
 * This interactor's implementation was not hidden behind an interface, because of
 */
class TrafficLightInteractor @Inject constructor(
    private val strategy: TrafficLightStrategy,
    private val repository: TrafficLightRepository
) {

    /**
     * Strictly init with submitted light
     */
    fun init(light: TrafficLight): TrafficFeatureState {
        val state = TrafficFeatureState(previous = null, current = light)
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
        return next(lastState)
    }

}