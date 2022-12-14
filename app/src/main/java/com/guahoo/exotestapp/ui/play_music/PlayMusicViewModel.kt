package com.guahoo.exotestapp.ui.play_music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.guahoo.exotestapp.repository.ITracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class PlayMusicViewModel : ViewModel(), KoinComponent {
    private val trackRepository: ITracksRepository = get()


    fun currentMetaData() = trackRepository.getCurrentMetaData()



    fun getTracks(): MutableLiveData<MutableList<MediaItem>>{
        return trackRepository.getFetchedTracks()
    }

    fun fetchTracks(){
        viewModelScope.launch(Dispatchers.IO) {
            trackRepository.fetchTracks()
        }

    }
}

