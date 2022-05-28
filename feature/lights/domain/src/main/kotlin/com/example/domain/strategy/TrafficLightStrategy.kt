package com.example.domain.strategy

import com.example.domain.model.TrafficLight

interface TrafficLightStrategy {

    fun nextLight(previous: TrafficLight?, current: TrafficLight?) : TrafficLight

}