package com.example.common

import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.Executor

interface SchedulersFactory {

    val computation: Scheduler
    val newThread: Scheduler
    val work: Scheduler
    val main: Scheduler

}