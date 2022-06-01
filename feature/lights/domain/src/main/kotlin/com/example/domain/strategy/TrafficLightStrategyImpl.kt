package com.example.domain.strategy

import com.example.domain.model.TrafficLight

/**
 * In order to add a little bit difficulty, we're modeling traffic Light, which lights yellow
 * in both ways - up and down, i.e.
 * Red -> Yellow -> Green -> Yellow -> Red and so on.
 *
 * So, when yellow light is lit, one can not decide whether next light should be red or green
 * without knowing the previous state.
 *
 */
class TrafficLightStrategyImpl : TrafficLightStrategy {

    override fun nextLight(previous: TrafficLight?, current: TrafficLight?): TrafficLight {
        return when {
            previous == null && current == null ->
                throw IllegalArgumentException("must specify either previous or current light state")
            previous != null && current == null -> previous
            previous == null && current != null -> nextNonNullLight(current, current)
            previous != null && current != null -> nextNonNullLight(previous, current)
            else -> throw IllegalStateException("how did you get here? :)")
        }
    }

    private fun nextNonNullLight(previous: TrafficLight, current: TrafficLight): TrafficLight {
        if (current == TrafficLight.GREEN)
            return TrafficLight.YELLOW

        if (current == TrafficLight.RED)
            return TrafficLight.YELLOW

        //current is Yellow
        if (previous == TrafficLight.RED)
            return TrafficLight.GREEN

        //current is Yellow, previous is TrafficLight.GREEN
        return TrafficLight.RED
    }

}