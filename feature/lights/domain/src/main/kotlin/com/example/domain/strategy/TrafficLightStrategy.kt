package com.example.domain.strategy

import com.example.domain.model.TrafficLight

/**
 * Strategy represents behavior that potentially could be changed in the future,
 * so usually it is a good idea to hide strategy's implementation behind an interface.
 */
interface TrafficLightStrategy {

    fun nextLight(previous: TrafficLight?, current: TrafficLight?): TrafficLight

}