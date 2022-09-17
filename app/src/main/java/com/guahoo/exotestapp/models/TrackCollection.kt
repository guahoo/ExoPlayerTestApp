package com.guahoo.exotestapp.models

data class TrackCollection(
    val track: Map<String, TrackDataModel>,
    val people: People
)
