package com.guahoo.exotestapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.guahoo.exotestapp.extensions.AppResult
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.models.AlbumModel

interface IAlbumsRepository {
     fun getAlbums(): LiveData<PagingData<AlbumDataModel>>
}