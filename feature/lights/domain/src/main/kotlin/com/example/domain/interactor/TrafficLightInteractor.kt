package com.example.domain.interactor

import com.example.domain.model.TrafficFeatureState
import javax.inject.Inject

/**
 * Personally, I do prefer Reactive Flows for Android OS has so many async work.
 * You can use whatever you like - RxJava, Jetbrains Flows, etc.
 *
 * It makes sense to design domain layer as simple as possible, which therefore
 * brings us to using blocking (synchronous) interface.
 * So, you could reuse domain logic in another project/platform/etc. and you won't
 * depend on particular async framework (like Flow or Rx).
 */
class TrafficLightInteractor @Inject constructor() {



}