package com.guahoo.exotestapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.guahoo.exotestapp.models.AlbumDataModel

interface IAlbumsRepository {
     fun getAlbums(): LiveData<PagingData<AlbumDataModel>>
}