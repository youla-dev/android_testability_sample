package com.example.lightson.screen

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.example.lightson.R
import com.example.lightson.TestApplication
import com.example.lightson.TrafficLightActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.text.KButton

object TrafficScreen : KScreen<TrafficScreen>() {

    override val layoutId: Int = R.layout.activity_traffic
    override val viewClass: Class<*> = TrafficLightActivity::class.java

    val background = KView { withId(R.id.traffic_root) }
    val button = KButton { withId(R.id.traffic_switch_btn) }
    val progress = KProgressBar { withId(R.id.traffic_progress_bar) }

    fun launch() {
        val intent = Intent(ApplicationProvider.getApplicationContext<TestApplication>(), TrafficLightActivity::class.java)
        val activityScenario = launchActivity<TrafficLightActivity>(intent)
        activityScenario.moveToState(Lifecycle.State.RESUMED)
    }

}