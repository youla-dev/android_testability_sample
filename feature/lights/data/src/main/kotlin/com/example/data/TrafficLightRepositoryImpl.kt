package com.example.data

import android.content.SharedPreferences
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository

class TrafficLightRepositoryImpl(
    private val shp: SharedPreferences
) : TrafficLightRepository {

    private val previousKey = "previous_light"
    private val currentKey = "current_light"

    override fun saveState(state: TrafficFeatureState): Boolean {
        return shp.edit()
            .putString(previousKey, state.previous?.name)
            .putString(currentKey, state.current.name)
            .commit()
    }

    override fun restoreState(): TrafficFeatureState? {
        val previousStr = shp.getString(previousKey, null)
        val currentStr = shp.getString(currentKey, null)

        val previous : TrafficLight? = previousStr?.let { TrafficLight.valueOf(it) }
        val current : TrafficLight? =  currentStr?.let { TrafficLight.valueOf(it) }

        return if (current == null)
            null
        else
            TrafficFeatureState(previous, current)
    }

}