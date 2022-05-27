package com.example.lightson.model

import androidx.annotation.ColorInt
import com.example.contract.ui.UIState

data class TrafficUIState(
    @ColorInt val backgroundColor: Int,
    val buttonText: String
): UIState