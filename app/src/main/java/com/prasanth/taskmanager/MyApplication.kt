package com.prasanth.taskmanager

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.perf.FirebasePerformance
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
     FirebaseAnalytics.getInstance(this)
     FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
     FirebasePerformance.getInstance()




}}