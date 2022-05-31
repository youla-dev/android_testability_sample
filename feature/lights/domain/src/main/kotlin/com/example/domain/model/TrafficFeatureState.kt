package com.example.domain.model

data class TrafficFeatureState(
    val previous: TrafficLight? = null,
    val current: TrafficLight
)