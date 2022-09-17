package com.guahoo.exotestapp.di

import com.guahoo.exotestapp.network.AppApi
import com.guahoo.exotestapp.repository.AlbumRepository
import com.guahoo.exotestapp.repository.IAlbumsRepository
import com.guahoo.exotestapp.repository.ITracksRepository
import com.guahoo.exotestapp.repository.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    fun provideTrackRepository(api: AppApi): ITracksRepository {
        return TracksRepository(api)
    }

    single {
        provideTrackRepository(get())
    }

    fun provideAlbumRepository(api: AppApi): IAlbumsRepository {
        return AlbumRepository(api)
    }

    single {
       provideAlbumRepository(get())
    }
}