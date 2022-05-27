package com.example.lightson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.common.CompositeDisposablesMap
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

    private lateinit var background: View
    private lateinit var button: Button

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
        button.setOnClickListener { vm.consume(TrafficUIEvent.Next()) }
    }

    private fun subscribeVM() {
        vm = vmFactory.create().of(this)
        disposablesMap["traffic_state"] = vm
            .states
            .filter { it.isCorrect }
            //.observeOn(Scd)
            .subscribe { acceptState(it) }
    }

    private fun acceptState(les: LES<TrafficUIState>) {
        //Improvement - cache last state and then check equals

        //todo switch defaults
        if (les.isLoading) {
            //todo show loading
        }
        if (les.isError) {
            //todo show error
        }
        val uiState = les.state
        if (uiState != null) {
            background.setBackgroundColor(uiState.backgroundColor)
            button.text = uiState.buttonText
        }
    }

}