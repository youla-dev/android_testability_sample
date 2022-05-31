package com.example.domain.repository

import com.example.domain.model.TrafficFeatureState

/*
 * Personally, I DO prefer Reactive Flows everywhere because of Android OS has so many async work.
 * You can use whatever you like - RxJava, Jetbrains Flows, etc.
 *
 * But in this case I decided to try the opposite way.
 *
 * It makes sense to design domain layer as simple as possible, which therefore
 * brings us to using blocking (synchronous) interface.
 * So, you could reuse domain logic in another project/platform/etc. and you won't
 * depend on particular async framework (like Flow or Rx).
 */
interface TrafficLightRepository {
    /**
     * Returns whether saving state was successful
     */
    fun saveState(state: TrafficFeatureState): Boolean

    /**
     * Returns last saved state
     */
    fun restoreState(): TrafficFeatureState?
}