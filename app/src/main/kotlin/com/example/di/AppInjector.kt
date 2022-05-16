package com.example.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

class AppInjector(appComponent: AppComponent, application: Application) {

    val androidInjector = appComponent.dispatchingAndroidInjector

    init {
        val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                //you might use this method, if you're going to use FragmentFactories
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivityPreCreated(activity)
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                clearSubcomponent()
            }
        }
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private fun handleActivityPreCreated(activity: Activity) {
        if (activity is HasAndroidInjector) {
            //skipping custom subcomponents for simplicity
            AndroidInjection.inject(activity)
            injectFragments(activity)
        }
    }

    private fun injectFragments(activity: Activity) {
        if (activity is AppCompatActivity) {
            val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

                override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                    //skipping custom subcomponents for simplicity
                    if (f is Injectable)
                        AndroidSupportInjection.inject(f)
                    super.onFragmentAttached(fm, f, context)
                }

                override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                    //skipping custom subcomponents for simplicity
                    super.onFragmentDetached(fm, f)
                }
            }
            val recursive = true
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, recursive)
        }
    }

    private fun clearSubcomponent() {
        //skipping custom subcomponents for simplicity
    }

}