package com.guahoo.exotestapp.ui.track_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.guahoo.exotestapp.extensions.AppResult
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.models.TrackDataModel
import com.guahoo.exotestapp.repository.ITracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TrackListViewModel : ViewModel(), KoinComponent {

    val trackRepository: ITracksRepository = get()
    val needShowLoader = MutableLiveData<Boolean>()

    fun getTracksInfo(): MutableLiveData<MutableList<TrackDataModel>> {
        return trackRepository.getTracksInfo()
    }

    fun invalidateTracks(){
        trackRepository.invalidateTracks()
    }



    fun fetchTrackInfo(trackId: Int){
        viewModelScope.launch (Dispatchers.IO){
            needShowLoader.postValue(true)
           val response = trackRepository.fetchTrackInfo(trackId)

            when(response){

                is AppResult.Success -> {
                    needShowLoader.postValue(false)
                }

                is AppResult.Error -> {

                }
            }

        }
    }
}