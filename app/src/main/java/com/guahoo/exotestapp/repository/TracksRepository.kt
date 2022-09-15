package com.guahoo.exotestapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TracksRepository(): ITracksRepository {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val tracks = MutableLiveData<MutableList<MediaItem>>()

    override fun getFetchedTracks(): MutableLiveData<MutableList<MediaItem>> {
        return tracks
    }

    override fun fetchTracks(){
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