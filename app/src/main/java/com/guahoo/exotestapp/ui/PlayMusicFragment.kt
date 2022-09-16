package com.guahoo.exotestapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.util.Util
import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.service.AudioPlayerService
import kotlinx.android.synthetic.main.fragment_play_music.*


class PlayMusicFragment : Fragment() {

    private var localAudioService: AudioPlayerService? = null
    private var mBound = false
    private lateinit var viewModel: PlayMusicViewModel

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder: AudioPlayerService.LocalBinder = iBinder as AudioPlayerService.LocalBinder
            localAudioService = binder.service
            mBound = true
            initializePlayer()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBound = false
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_music, container, false)
    }


    private fun initializePlayer() {
        if (mBound) {
            val player: ExoPlayer? = localAudioService?.getPlayerInstance()
            exoplayerview.player = player

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(requireContext(),AudioPlayerService::class.java).also {
            Util.startForegroundService(requireContext(),it)

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PlayMusicViewModel::class.java]
        viewModel.fetchTracks()
        exoplayerview.useController = true
        exoplayerview.controllerShowTimeoutMs = 0
        exoplayerview.showController()
        exoplayerview.controllerAutoShow = true
        exoplayerview.controllerHideOnTouch = false

    }

    override fun onStart() {
        super.onStart()
            Intent(requireContext(), AudioPlayerService::class.java).also { intent ->
                requireActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
            }
    }

}