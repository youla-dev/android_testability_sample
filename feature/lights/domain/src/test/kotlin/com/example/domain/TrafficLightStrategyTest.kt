package com.example.domain

import com.example.domain.model.TrafficLight
import com.example.domain.strategy.TrafficLightStrategy
import com.example.domain.strategy.TrafficLightStrategyImpl
import org.junit.Assert
import org.junit.Test

class TrafficLightStrategyTest {

    private val strategy : TrafficLightStrategy = TrafficLightStrategyImpl()

    @Test
    fun `with current green light expect next yellow light`() {
        val nextLight = strategy.nextLight(null, TrafficLight.GREEN)
        Assert.assertEquals(TrafficLight.YELLOW, nextLight)
    }

    @Test
    fun `with current red light expect next yellow light`() {
        val nextLight = strategy.nextLight(null, TrafficLight.RED)
        Assert.assertEquals(TrafficLight.YELLOW, nextLight)
    }

    @Test
    fun `with current yellow light and previous red expect next green light`() {
        val nextLight = strategy.nextLight(TrafficLight.RED, TrafficLight.YELLOW)
        Assert.assertEquals(TrafficLight.GREEN, nextLight)
    }

    @Test
    fun `with current yellow light and previous null expect red`() {
        val nextLight = strategy.nextLight(null, TrafficLight.YELLOW)
        Assert.assertEquals(TrafficLight.RED, nextLight)
    }

    //the rest is task for a reader =)

}
