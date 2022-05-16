package com.example.common

import io.reactivex.rxjava3.disposables.Disposable

class CompositeDisposablesMap {

    private val disposables = mutableMapOf<String, Disposable>()

    @Synchronized
    fun clear(key: String) {
        val disposable = disposables.remove(key) ?: return
        disposable.disposeIfNeeded()
    }

    @Synchronized
    fun clearAll() {
        disposables
            .map { it.value }
            .apply { disposables.clear() }
            .forEach { it.disposeIfNeeded() }
    }

    @Synchronized
    fun containsKey(key: String): Boolean {
        return disposables.containsKey(key)
    }

    @Synchronized
    fun isDisposed(key: String): Boolean {
        val disposable = disposables[key] ?: return true
        return disposable.isDisposed
    }

    @Synchronized
    fun put(key: String, disposable: Disposable) {
        clear(key)
        disposables[key] = disposable
    }

    operator fun set(key: String, disposable: Disposable) {
        put(key, disposable)
    }

    private fun Disposable.disposeIfNeeded() {
        if (!isDisposed) dispose()
    }

}