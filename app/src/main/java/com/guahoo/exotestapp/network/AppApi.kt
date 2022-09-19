package com.guahoo.exotestapp.network

import com.guahoo.exotestapp.extensions.AppResult
import com.guahoo.exotestapp.models.AlbumRequestModel
import com.guahoo.exotestapp.models.TrackRequestModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("?method=product.getNews")
    suspend fun getAllAlbums(
        @Query("limit") limit: Int = 2,
        @Query("page") page: Int
    ): Response<AlbumRequestModel>


    @GET("?method=product.getCard")
    suspend fun getTracksInfo(
        @Query("productId") productId: Int,
    ): Response<TrackRequestModel>




}