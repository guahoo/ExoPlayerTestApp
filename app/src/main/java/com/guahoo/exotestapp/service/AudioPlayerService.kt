package com.guahoo.exotestapp.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.common.util.concurrent.Futures
import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.repository.ITracksRepository
import com.guahoo.exotestapp.ui.MainActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.util.*


class AudioPlayerService : LifecycleService(), KoinComponent {
    private val trackRepository: ITracksRepository = get()


    var player: ExoPlayer? = null
    private var playerNotificationManager: PlayerNotificationManager? = null
    private var mediaSession: MediaSessionCompat? = null
//    private var mediaSessionConnector: MediaSessionConnector? = null
//    private var allInOneApplication: AllInOneApplication? = null

    private val binder = LocalBinder()

    override fun onDestroy() {
        mediaSession?.release()
        //  mediaSessionConnector?.setPlayer(null, null)
        playerNotificationManager?.setPlayer(null)
        player?.release()
        player = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return binder
    }

    fun getPlayerInstance(): ExoPlayer? {


//        if (player == null) {
//            startPlayer()
//        }
        return player
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (player == null) {
            trackRepository.getFetchedTracks().observe(this) { mediaItems ->
                if (player == null) {
                    player = ExoPlayer.Builder(this).build()
                }
                startPlayer()
                player?.apply {
                    addMediaItems(mediaItems)
                    playWhenReady = true
                    prepare()
                }
            }
        }
        return START_STICKY
    }

    private fun startPlayer() {


        playerNotificationManager = PlayerNotificationManager.Builder(this,
            101,
            "channelId",
            object : PlayerNotificationManager.MediaDescriptionAdapter {

                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return getPlayerInstance()?.mediaMetadata?.title ?: ""
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    val intent = Intent(this@AudioPlayerService, MainActivity::class.java)
                    return PendingIntent.getActivity(
                        this@AudioPlayerService,
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                }

                override fun getCurrentContentText(player: Player): CharSequence {
                    return getPlayerInstance()?.mediaMetadata?.artist ?: ""
                }


                override fun getCurrentSubText(player: Player): CharSequence {
                    return ""
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null // add artwork here
                }
            }).setNotificationListener(object : PlayerNotificationManager.NotificationListener {

            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
                super.onNotificationPosted(notificationId, notification, ongoing)
                if (ongoing)
                    startForeground(notificationId, notification)
                else
                    stopForeground(false)
            }
        }).setChannelNameResourceId(R.string.channel_name)
            .setChannelDescriptionResourceId(R.string.channel_desc)
            .build()

        playerNotificationManager?.apply {
            setUseChronometer(true)
            setUseNextActionInCompactView(true)
            setUsePreviousActionInCompactView(true)
            setUsePlayPauseActions(true)
            setPlayer(player)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        }


    }


    inner class LocalBinder : Binder() {
        val service: AudioPlayerService
            get() = this@AudioPlayerService
    }


}