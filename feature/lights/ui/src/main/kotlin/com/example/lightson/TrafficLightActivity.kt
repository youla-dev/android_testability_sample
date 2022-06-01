package com.example.lightson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.common.CompositeDisposablesMap
import com.example.common.SchedulersFactory
import com.example.common.ViewModelFactory
import com.example.common.create
import com.example.contract.ui.model.LES
import com.example.domain.model.TrafficLight
import com.example.lightson.model.TrafficUIEvent
import com.example.lightson.model.TrafficUIState
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TrafficLightActivity : AppCompatActivity(), HasAndroidInjector  {

    private val disposablesMap = CompositeDisposablesMap()
    @Inject
    lateinit var dispatchingFragmentsInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingFragmentsInjector

    @Inject
    lateinit var schedulersFactory: SchedulersFactory

    private lateinit var background: View
    private lateinit var button: Button
    private lateinit var progress: ProgressBar

    @Inject
    lateinit var vmFactory: ViewModelFactory<TrafficLightsVm>
    lateinit var vm: TrafficLightsVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic)

        setupView()
        subscribeVM()

        //Initial common setup, you could use repository pattern downstream
        if (savedInstanceState != null) {
            vm.consume(TrafficUIEvent.Restore())
        } else {
            vm.consume(TrafficUIEvent.Init(color = TrafficLight.GREEN))
        }
    }

    private fun setupView() {
        background = findViewById(R.id.traffic_root)
        button = findViewById(R.id.traffic_switch_btn)
        progress = findViewById(R.id.traffic_progress_bar)
        button.setOnClickListener { vm.consume(TrafficUIEvent.Next()) }
    }

    private fun subscribeVM() {
        vm = vmFactory.create().of(this)
        disposablesMap["traffic_state"] = vm
            .viewStates
            .filter { it.isCorrect }
            .observeOn(schedulersFactory.main)
            .subscribe { acceptState(it) }
    }

    private fun acceptState(les: LES<TrafficUIState>) {
        //Improvement - cache last state and then check equals

        progress.visibility = if (les.isLoading) View.VISIBLE else View.GONE
        if (les.isError) {
            //task for a reader - make errors =)
        }

        val uiState = les.state
        if (uiState != null) {
            button.visibility = View.VISIBLE
            background.setBackgroundColor(uiState.backgroundColor)
            button.text = uiState.buttonText
        }
    }

}