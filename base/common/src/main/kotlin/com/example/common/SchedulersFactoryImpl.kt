package com.example.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulersFactoryImpl @Inject constructor() : SchedulersFactory {

    override val main: Scheduler by lazy { AndroidSchedulers.mainThread() }
    override val computation: Scheduler by lazy { Schedulers.computation() }
    override val newThread: Scheduler by lazy { Schedulers.newThread() }
    override val work: Scheduler by lazy { Schedulers.io() }

}