package com.guahoo.exotestapp.models

data class Collection(
    val album: Map<String, AlbumDataModel>,
    val people: People
)