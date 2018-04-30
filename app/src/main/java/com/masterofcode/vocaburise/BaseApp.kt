package com.masterofcode.vocaburise

import android.annotation.SuppressLint
import android.app.Application

/**
 * Created by andrews on 30.04.18.
 */

abstract class App : Application() {
    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var app: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}