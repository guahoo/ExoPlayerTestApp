package com.guahoo.exotestapp.ui.albums_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.repository.IAlbumsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AlbumListViewModel : ViewModel(), KoinComponent {
   private val albumRepository: IAlbumsRepository = get()
    val isRefreshing = MutableLiveData<Boolean>()
    private var mPagingData : Flow<PagingData<AlbumDataModel>>? = null



    fun loadAlbums(): Flow<PagingData<AlbumDataModel>> {
        return if(mPagingData != null){
            mPagingData!!
        } else {
            mPagingData = albumRepository.getAlbums().cachedIn(viewModelScope)
            mPagingData as Flow<PagingData<AlbumDataModel>>
        }
    }
}