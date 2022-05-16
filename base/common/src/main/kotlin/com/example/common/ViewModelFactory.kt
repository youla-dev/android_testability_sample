package com.example.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory<T : ViewModel> @Inject constructor(private val viewModel: Provider<T>) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel.get() as T
    }

}

inline fun <reified T : ViewModel> ViewModelFactory<T>.create(): ViewModelRequest<T> = ViewModelRequest(this, T::class.java)