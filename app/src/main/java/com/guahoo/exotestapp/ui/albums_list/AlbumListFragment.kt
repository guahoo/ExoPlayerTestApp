package com.guahoo.exotestapp.ui.albums_list

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.databinding.AlbumItemBinding
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.ui.albums_list.adapters.AlbumsAdapter
import com.guahoo.exotestapp.ui.track_list.TrackListFragmentDirections
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlbumListFragment : Fragment() {

    private lateinit var albumAdapter : AlbumsAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        albumAdapter =  AlbumsAdapter { album ->
            findNavController().navigate(
              AlbumListFragmentDirections.actionAlbumListFragmentToTrackListFragment(
                    albumid = album.id?.toInt() ?: 0
                )
            )
        }
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(AlbumListViewModel::class.java)

        swiperefreshlayout.setOnRefreshListener(refreshListener)

        rw_rest.apply {
            adapter = albumAdapter
            Log.v("List1234", "${albumAdapter!!.itemCount}")
            layoutManager = LinearLayoutManager(requireContext())
        }

        lifecycleScope.launchWhenCreated {
            viewModel.loadAlbums().collectLatest{pagingData->

                albumAdapter.submitData(lifecycle, pagingData)
                Log.v("List1234", "${albumAdapter!!.itemCount}")
            }

        }



        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            swiperefreshlayout.isRefreshing = it
            viewModel.loadAlbums()
        }


        super.onViewCreated(view, savedInstanceState)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                    Toast.makeText(requireContext(), "Проблема соединения с сервером", Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}