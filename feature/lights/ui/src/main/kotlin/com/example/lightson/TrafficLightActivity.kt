package com.example.lightson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.common.CompositeDisposablesMap
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TrafficLightActivity : AppCompatActivity(), HasAndroidInjector  {

    private val disposablesMap = CompositeDisposablesMap()
    @Inject
    lateinit var dispatchingFragmentsInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingFragmentsInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic)

    }

}