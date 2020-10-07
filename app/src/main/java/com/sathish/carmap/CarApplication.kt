package com.sathish.carmap

import android.app.Application
import com.sathish.carmap.di.networkModule
import com.sathish.carmap.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CarApplication : Application () {

    override fun onCreate() {
        super.onCreate()
        configureDi()

    }

    private fun configureDi() {
        startKoin {
            androidContext(this@CarApplication)
            androidLogger()
            modules(listOf(networkModule, viewModelModule))
        }
    }




}