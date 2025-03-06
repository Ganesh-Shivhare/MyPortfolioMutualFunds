package com.ganesh.hilt.mutualfund.sip.myportfolio

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var myApplication: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        // Register lifecycle observer
        myApplication = this
    }
}