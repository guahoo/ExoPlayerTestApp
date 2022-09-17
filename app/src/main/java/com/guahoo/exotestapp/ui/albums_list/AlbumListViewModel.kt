package com.guahoo.exotestapp.ui.albums_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.repository.IAlbumsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AlbumListViewModel : ViewModel(), KoinComponent {
   private val albumRepository: IAlbumsRepository = get()
    val isRefreshing = MutableLiveData<Boolean>()

    fun loadAlbums(): LiveData<PagingData<AlbumDataModel>> {
        return albumRepository.getAlbums().cachedIn(viewModelScope)
    }


}