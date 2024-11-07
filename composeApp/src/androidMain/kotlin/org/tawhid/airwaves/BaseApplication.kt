package org.tawhid.airwaves


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.tawhid.airwaves.di.initKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}