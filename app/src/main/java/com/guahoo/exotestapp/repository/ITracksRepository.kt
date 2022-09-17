package com.guahoo.exotestapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.guahoo.exotestapp.extensions.AppResult
import com.guahoo.exotestapp.models.TrackDataModel
import com.guahoo.exotestapp.models.TrackRequestModel

interface ITracksRepository {
    fun getFetchedTracks(): MutableLiveData<MutableList<MediaItem>>
    fun getTracksInfo(): MutableLiveData<MutableList<TrackDataModel>>
    suspend fun fetchTrackInfo(idAlbum: Int):AppResult<TrackRequestModel>
    suspend fun fetchTracks()
    fun invalidateTracks()
    fun renewCurrentMetaData(mediaMetadata: MediaMetadata?)
    fun getCurrentMetaData(): MutableLiveData<MediaMetadata?>
}