package com.guahoo.exotestapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.guahoo.exotestapp.extensions.AppResult
import com.guahoo.exotestapp.extensions.handleApiError
import com.guahoo.exotestapp.extensions.handleSuccess
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.models.AlbumModel
import com.guahoo.exotestapp.network.AppApi
import com.guahoo.exotestapp.source.AlbumsDataSource
import kotlinx.coroutines.coroutineScope

class AlbumRepository(val api: AppApi): IAlbumsRepository {

    override fun getAlbums(): LiveData<PagingData<AlbumDataModel>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
               AlbumsDataSource(api)
            }
        ).liveData
    }
}