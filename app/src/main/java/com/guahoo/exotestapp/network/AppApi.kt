package com.guahoo.exotestapp.network

import com.guahoo.exotestapp.models.AlbumModel
import com.guahoo.exotestapp.models.RequestModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("method=product.getNews")
    suspend fun getAllAlbums(
        @Query("limit") limit: Int = 3,
        @Query("page") page: Int
    ): Response<AlbumModel>


    @GET("method=product.getCard")
    suspend fun getTracks(
        @Query("productId") productId: Int,
    ): Response<AlbumModel>




}