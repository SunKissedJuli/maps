package com.example.maps

import android.app.Application
import com.example.maps.di.KoinInjector
import org.koin.android.ext.koin.androidContext

class MapsApp : Application() {
    override fun onCreate() {
        super.onCreate()


        KoinInjector.koinApp
            .androidContext(this@MapsApp)

    }
}