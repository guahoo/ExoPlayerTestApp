package com.guahoo.exotestapp.di

import android.content.Context
import com.guahoo.exotestapp.repository.ITracksRepository
import com.guahoo.exotestapp.repository.TracksRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    fun provideTrackRepository(): ITracksRepository {
        return TracksRepository()
    }

    single {
        provideTrackRepository()
    }
}