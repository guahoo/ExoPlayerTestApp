package com.guahoo.exotestapp.models

data class AlbumRequestModel(
    val collection: Collection,
    val error: Error,
    val response: Response
)