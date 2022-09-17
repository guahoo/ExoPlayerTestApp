package com.guahoo.exotestapp

import android.app.Application
import com.guahoo.exotestapp.di.networkModule
import com.guahoo.exotestapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class ExoTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR)
            androidContext(this@ExoTestApplication)
            modules(listOf(repositoryModule, networkModule))
        }
    }

}
