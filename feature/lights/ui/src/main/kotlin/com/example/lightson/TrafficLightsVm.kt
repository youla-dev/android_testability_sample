package com.example.lightson

import androidx.lifecycle.ViewModel
import com.example.common.CompositeDisposablesMap
import com.example.common.SchedulersFactory
import com.example.contract.ui.UIEvent
import com.example.contract.ui.model.LES
import com.example.domain.interactor.TrafficLightInteractor
import com.example.domain.model.TrafficFeatureState
import com.example.lightson.mapper.TrafficStateMapper
import com.example.lightson.model.TrafficUIEvent
import com.example.lightson.model.TrafficUIState
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrafficLightsVm @Inject constructor(
    private val interactor: TrafficLightInteractor,
    private val schedulersFactory: SchedulersFactory,
    private val mapper: TrafficStateMapper
) : ViewModel() {

    private val disposableMap = CompositeDisposablesMap()
    private val loading = BehaviorProcessor.create<Boolean>()
    private val data = BehaviorProcessor.create<TrafficUIState>()

    val viewStates: Flowable<LES<TrafficUIState>> = Flowable.combineLatest(
        loading, data
    ) { load, data -> LES(
        isLoading = load,
        state = data
    )}

    /**
     * In production code this method IS THE PART of the "Interface".
     * It may be Consumer / AbsViewModel / etc ...
     */
    fun consume(event: UIEvent) {
        when(event) {
            is TrafficUIEvent.Init -> turnOnInitialLight(event)
            is TrafficUIEvent.Restore -> restoreSavedLight()
            is TrafficUIEvent.Next -> switchNextLight()
            else -> throw IllegalArgumentException("$event is not supported in $this")
        }
    }

    private fun switchNextLight() {
        val isLoading = loading.value ?: false
        if (isLoading)
            return
        disposableMap["vm_key_init"] = Single
            .fromCallable { interactor.nextState() }
            .doOnSubscribe { loading.onNext(true) }
            .map { mapper.map(it) }
            .delay(2000, TimeUnit.MILLISECONDS) //just to emulate work
            .subscribeOn(schedulersFactory.work)
            .observeOn(schedulersFactory.main)
            .doOnEvent { _, _ -> loading.onNext(false) }
            .subscribe(
                { data.onNext(it) },
                { /* task for a reader - send error */ }
            )
    }

    private fun restoreSavedLight() {
        disposableMap["vm_key_init"] = Single
            .fromCallable { interactor.initWithLastSaved() }
            .doOnSubscribe { loading.onNext(true) }
            .map { mapper.map(it) }
            .subscribeOn(schedulersFactory.work)
            .observeOn(schedulersFactory.main)
            .observeOn(schedulersFactory.main)
            .doOnEvent { _, _ -> loading.onNext(false) }
            .subscribe(
                { data.onNext(it) },
                { /* task for a reader - send error */ }
            )
    }

    private fun turnOnInitialLight(event: TrafficUIEvent.Init) {
        val color = event.color
        if (color == null) {
            consume(TrafficUIEvent.Restore())
            return
        }
        disposableMap["vm_key_init"] = Single
            .fromCallable { interactor.init(TrafficFeatureState(
                previous = null,
                current = color
            )) }
            .doOnSubscribe { loading.onNext(true) }
            .map { mapper.map(it) }
            .subscribeOn(schedulersFactory.work)
            .observeOn(schedulersFactory.main)
            .observeOn(schedulersFactory.main)
            .doOnEvent { _, _ -> loading.onNext(false) }
            .subscribe(
                { data.onNext(it) },
                { /* task for a reader - send error */ }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposableMap.clearAll()
    }

}