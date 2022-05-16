package com.example.lightson

import androidx.lifecycle.ViewModel
import com.example.common.CompositeDisposablesMap

class TrafficLightsVm : ViewModel() {

    private val disposableMap = CompositeDisposablesMap()

    override fun onCleared() {
        super.onCleared()
    }

}