package com.guahoo.exotestapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.network.AppApi
import com.guahoo.exotestapp.source.AlbumsDataSource
import kotlinx.coroutines.flow.Flow

class AlbumRepository(val api: AppApi): IAlbumsRepository {

    override fun getAlbums(): Flow<PagingData<AlbumDataModel>> {

        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
               AlbumsDataSource(api)
            }
        ).flow
    }
}