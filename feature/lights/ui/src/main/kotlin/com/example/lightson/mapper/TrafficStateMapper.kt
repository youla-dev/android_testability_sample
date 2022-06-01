package com.example.lightson.mapper

import android.graphics.Color
import androidx.annotation.ColorInt
import com.example.contract.domain.ResourceRepository
import com.example.domain.model.TrafficFeatureState
import com.example.domain.model.TrafficLight
import com.example.lightson.R
import com.example.lightson.model.TrafficUIState
import javax.inject.Inject

//Why we even bother with resources work in mapper
//The reason is simple: when we could do some work off the main thread
//we should do it (with rare exceptions)
class TrafficStateMapper @Inject constructor(
    private val resourceRepository: ResourceRepository
) {

    private val red: Int by lazy(mode = LazyThreadSafetyMode.NONE) {
        Color.parseColor("#ff0000")
    }
    private val yellow: Int by lazy(mode = LazyThreadSafetyMode.NONE) {
        Color.parseColor("#ffff00")
    }
    private val green: Int by lazy(mode = LazyThreadSafetyMode.NONE) {
        Color.parseColor("#00ff00")
    }

    fun map(domainState: TrafficFeatureState): TrafficUIState {
        val currentLight = domainState.current
        val previousLight = domainState.previous

        //these colors are super simple, so we put it here
        //Best practise is get those from resources, as we do it with strings
        @ColorInt val backgroundColor = when (domainState.current) {
            TrafficLight.RED -> red
            TrafficLight.YELLOW -> yellow
            TrafficLight.GREEN -> green
        }

        //As you can see, string is from the resources repository
        //You can get color same way
        val colorPart : String = when {
            currentLight == TrafficLight.RED -> resourceRepository.getString(R.string.attention)
            currentLight == TrafficLight.GREEN -> resourceRepository.getString(R.string.attention)
            currentLight == TrafficLight.YELLOW && previousLight == TrafficLight.GREEN ->
                resourceRepository.getString(R.string.stop)
            currentLight == TrafficLight.YELLOW && previousLight == TrafficLight.RED ->
                resourceRepository.getString(R.string.go)
            else ->
                throw IllegalStateException("did not supposed to be with" +
                        " current = $currentLight, previous = $previousLight")
        }
        val buttonPart = resourceRepository.getString(R.string.button_text)
        val buttonText = String.format(buttonPart, colorPart)
        return TrafficUIState(
            backgroundColor = backgroundColor,
            buttonText = buttonText
        )
    }

}