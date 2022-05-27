package com.example.lightson.model

import com.example.contract.ui.UIEvent
import com.example.domain.model.TrafficLight

sealed class TrafficUIEvent() : UIEvent {
    class Init(val color: TrafficLight? = null): TrafficUIEvent()
    class Restore: TrafficUIEvent()
    class Next: TrafficUIEvent()
}