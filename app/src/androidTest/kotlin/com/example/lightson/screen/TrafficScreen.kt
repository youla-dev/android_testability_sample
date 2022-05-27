package com.example.lightson.screen

import com.example.lightson.R
import com.example.lightson.TrafficLightActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton

object TrafficScreen : KScreen<TrafficScreen>() {

    override val layoutId: Int = R.layout.activity_traffic
    override val viewClass: Class<*> = TrafficLightActivity::class.java

    val background = KView { withId(R.id.traffic_root) }
    val button = KButton { withId(R.id.traffic_switch_btn) }

}