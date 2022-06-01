package com.example.utils

import androidx.test.espresso.IdlingRegistry
import com.example.common.SchedulersFactory
import com.example.common.SchedulersFactoryImpl
import com.squareup.rx3.idler.Rx3Idler
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class TestIdleSchedulersFactory @Inject constructor(
    private val schedulersFactoryImpl: SchedulersFactoryImpl
) : SchedulersFactory {

    override val computation: Scheduler by lazy {
        Rx3Idler.wrap(
            schedulersFactoryImpl.computation,
            "RxIdler computation"
        ).also {
            IdlingRegistry.getInstance().register(it)
        }
    }

    override val main: Scheduler by lazy {
        Rx3Idler.wrap(
            schedulersFactoryImpl.main,
            "RxIdler main"
        ).also {
            IdlingRegistry.getInstance().register(it)
        }
    }

    override val newThread: Scheduler by lazy {
        Rx3Idler.wrap(
            schedulersFactoryImpl.newThread,
            "RxIdler newThread"
        ).also {
            IdlingRegistry.getInstance().register(it)
        }
    }

    override val work: Scheduler by lazy {
        Rx3Idler.wrap(
            schedulersFactoryImpl.work,
            "RxIdler work"
        ).also {
            IdlingRegistry.getInstance().register(it)
        }
    }

}