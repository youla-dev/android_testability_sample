package com.example.lightson

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.common.CompositeDisposablesMap
import com.example.contract.ui.UIEvent
import com.example.contract.ui.model.LES
import com.example.domain.model.TrafficLight
import com.example.lightson.model.TrafficUIEvent
import com.example.lightson.model.TrafficUIState
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.BehaviorProcessor
import javax.inject.Inject

class TrafficLightsVm @Inject constructor(

) : ViewModel() {

    private val disposableMap = CompositeDisposablesMap()

    private val viewStateProcessor: BehaviorProcessor<LES<TrafficUIState>>
            by lazy(mode = LazyThreadSafetyMode.NONE) { BehaviorProcessor.create() }

    val states: Flowable<LES<TrafficUIState>>
    get() = viewStateProcessor

    /**
     * In production code this method IS THE PART of the "Interface".
     * It may be Consumer / AbsViewModel / etc ...
     */
    fun consume(event: UIEvent) {
        when(event) {
            is TrafficUIEvent.Init -> sendInitialState(event)
            //is TrafficUIEvent.Restore ->
            //is TrafficUIEvent.Next ->
            else -> throw IllegalArgumentException("$event is not supported in $this")
        }
    }

    private fun sendInitialState(event: TrafficUIEvent.Init) {
        val color = event.color
        if (color == null) {
            consume(TrafficUIEvent.Restore())
            return
        }
        //todo interactor
        val state = when (color) {
            TrafficLight.RED -> TrafficUIState(
                backgroundColor = Color.parseColor("#ff0000"),
                buttonText = "red"
            )
            TrafficLight.YELLOW -> TrafficUIState(
                backgroundColor = Color.parseColor("#ffff00"),
                buttonText = "yellow"
            )
            TrafficLight.GREEN -> TrafficUIState(
                backgroundColor = Color.parseColor("#00ff00"),
                buttonText = "green"
            )
        }
        sendState(state)
    }

    private fun sendState(st: TrafficUIState) {
        viewStateProcessor.offer(LES(
            state = st
        ))
    }

    override fun onCleared() {
        super.onCleared()
        disposableMap.clearAll()
    }

}