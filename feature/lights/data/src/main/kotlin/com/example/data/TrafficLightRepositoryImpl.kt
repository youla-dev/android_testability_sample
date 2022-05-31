package com.example.data

import android.content.SharedPreferences
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.domain.repository.TrafficLightRepository

/*
    Of course, in a production app usually you would use SavedStateHandle or something like that.
    Personally, I recommend either modeling ui restore as some `ui event`,
    or wrapping current fragment/activity in some component you would use to save data in
    the data layer.
    SharedPreferences here is just for a simplicity.
 */
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