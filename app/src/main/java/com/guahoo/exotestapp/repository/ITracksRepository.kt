package com.guahoo.exotestapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem

interface ITracksRepository {
    fun getFetchedTracks(): MutableLiveData<MutableList<MediaItem>>
    fun fetchTracks()
}