package com.guahoo.exotestapp.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.guahoo.exotestapp.models.AlbumDataModel
import com.guahoo.exotestapp.network.AppApi


class AlbumsDataSource (private val apiService: AppApi): PagingSource<Int, AlbumDataModel>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumDataModel> {
            return try {
                val position = params.key ?: 1
                val response = apiService.getAllAlbums(page = position)

                LoadResult.Page(data = response.body()!!.collection.album.values.toList(), prevKey = if (position == 1) null else position - 1,
                    nextKey = position + 1)

            } catch (e: Exception) {
                LoadResult.Error(e)
            }

        }

        override fun getRefreshKey(state: PagingState<Int, AlbumDataModel>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }

    }
