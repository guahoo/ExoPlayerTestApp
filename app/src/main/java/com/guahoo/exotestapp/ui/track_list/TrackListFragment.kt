package com.guahoo.exotestapp.ui.track_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.models.TrackDataModel
import com.guahoo.exotestapp.ui.play_music.PlayMusicFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_track_list.*

class TrackListFragment : Fragment() {

    private lateinit var viewModel: TrackListViewModel
    private val args: TrackListFragmentArgs by navArgs()

    private val tracksAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrackListViewModel::class.java)

        initList()

        rv_tracks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tracksAdapter
        }

        btn_back.setOnClickListener {
            viewModel.trackRepository.invalidateTracks()
            findNavController().navigateUp()        }

        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                viewModel.trackRepository.invalidateTracks()
                findNavController().navigateUp()
            }

        })




        viewModel.getTracksInfo().observe(viewLifecycleOwner){ trackList->

            Log.v("TrackList", "${trackList.size}")

            tracksAdapter.clear()
            tracksAdapter.addAll(trackList.toTrackDetailListItems())
        }

        viewModel.needShowLoader.observe(viewLifecycleOwner){ needShow ->

            if (needShow){
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }

        }

    }

    private fun initList() {
        tracksAdapter.clear()
        viewModel.fetchTrackInfo(trackId = args.albumid)
    }

    private fun MutableList<TrackDataModel>.toTrackDetailListItems(): List<TrackDetailsItem>{
        return this.map {
            TrackDetailsItem(trackModel = it){
                val playMusicFragment = PlayMusicFragment(it)
                playMusicFragment.show(
                    requireActivity().supportFragmentManager,
                    "PlayMusicFragment"
                )
            }
        }
    }


}