package com.guahoo.exotestapp.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.guahoo.exotestapp.extensions.AppResult
import com.guahoo.exotestapp.extensions.handleApiError
import com.guahoo.exotestapp.extensions.handleSuccess
import com.guahoo.exotestapp.models.TrackDataModel
import com.guahoo.exotestapp.models.TrackRequestModel
import com.guahoo.exotestapp.network.AppApi
import kotlinx.coroutines.*

class TracksRepository(private val api: AppApi): ITracksRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val tracks = MutableLiveData<MutableList<MediaItem>>()
    private val tracksInfo = MutableLiveData<MutableList<TrackDataModel>>()
    private val currentMediaMetadata = MutableLiveData<MediaMetadata?>()

    override fun renewCurrentMetaData(mediaMetadata: MediaMetadata?){
        currentMediaMetadata.postValue(mediaMetadata)
    }


    override fun getCurrentMetaData() = currentMediaMetadata


    override fun getFetchedTracks(): MutableLiveData<MutableList<MediaItem>> {
        return tracks
    }

    override fun getTracksInfo(): MutableLiveData<MutableList<TrackDataModel>> {
        return tracksInfo
    }

    override fun invalidateTracks(){
        tracksInfo.postValue(mutableListOf())
    }

    override suspend fun fetchTrackInfo(idAlbum: Int): AppResult<TrackRequestModel> =
        coroutineScope {
            return@coroutineScope try {
                val request = api.getTracksInfo(productId = idAlbum)

                if (request.isSuccessful) {
                    tracksInfo.postValue(request.body()?.let { convertingTrackRequestModel(it) })
                    handleSuccess(request)

                } else {
                    handleApiError(request)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        }


    private fun convertingTrackRequestModel(requestModel: TrackRequestModel): MutableList<TrackDataModel> {
        Log.v("Repos123","${requestModel.collection.track.size}")
        return requestModel.collection.track.values.toMutableList()
    }





    override suspend fun fetchTracks(){
        scope.launch {
            /**
             * адреса треков будут приходить из вьюмодели и скачиваться
             */
            val urlList = listOf(
                "https://storage.enazamusic.com/files/vacancy/test-files/file_example_MP3_5MG.mp3",
                "https://storage.enazamusic.com/files/vacancy/test-files/file_example_MP3_2MG.mp3",
                "https://ru.drivemusic.me/dl/71qtAH3-6DvVuGHpk3VDBw/1663276548/download_music/2014/05/nico-vinz-am-i-wrong.mp3"
            )


            val mediaSource = mutableListOf<MediaItem>()
            for (link in urlList) {
                mediaSource.add(MediaItem.fromUri(link))
            }
            tracks.postValue(mediaSource)
        }
    }
}