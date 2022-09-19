package com.guahoo.exotestapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.guahoo.exotestapp.models.AlbumDataModel
import kotlinx.coroutines.flow.Flow

interface IAlbumsRepository {
     fun getAlbums(): Flow<PagingData<AlbumDataModel>>
}