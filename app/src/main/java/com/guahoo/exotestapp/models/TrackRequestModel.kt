package com.guahoo.exotestapp.models



data class TrackRequestModel(
    val collection: TrackCollection,
    val error: Error,
    val response: Response
)
