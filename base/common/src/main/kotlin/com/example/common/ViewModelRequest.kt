package com.example.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelRequest<T: ViewModel>(
    private val factory: ViewModelFactory<T>, private val clazz: Class<T>
) {

    fun of(fragment: Fragment) : T = ViewModelProvider(fragment.viewModelStore, factory)[clazz]

    fun of(activity: FragmentActivity) : T = ViewModelProvider(activity.viewModelStore, factory)[clazz]
}