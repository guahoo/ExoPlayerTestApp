package com.guahoo.exotestapp.ui.albums_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.databinding.AlbumItemBinding
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.ui.albums_list.adapters.AlbumsAdapter
import com.guahoo.exotestapp.ui.track_list.TrackListFragmentDirections
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.launch

class AlbumListFragment : Fragment() {


    private val albumAdapter = AlbumsAdapter { album ->
        Log.v("AlbumListFragment", "Clicked")
        findNavController().navigate(
            AlbumListFragmentDirections.actionAlbumListFragmentToTrackListFragment(
                albumid = album.id?.toInt() ?: 0
            )
        )
    }
    private lateinit var viewModel: AlbumListViewModel

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.isRefreshing.postValue(true)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isRefreshing.postValue(false)
        }, 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumListViewModel::class.java)

        lifecycleScope.launch {
            viewModel.loadAlbums().observe(viewLifecycleOwner) {
                Log.v("AlbumListFragment", "${it.toString()}")
                it?.let { pagingData ->
                    albumAdapter.submitData(lifecycle, pagingData)
                }
            }
        }


        rw_rest.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        albumAdapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                progressDialog?.isVisible = true
            else {
                progressDialog?.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()

                }

            }
        }

        swiperefreshlayout.setOnRefreshListener(refreshListener)

        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            swiperefreshlayout.isRefreshing = it
            viewModel.loadAlbums()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        albumAdapter.addLoadStateListener {  }
    }



}