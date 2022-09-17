package com.guahoo.exotestapp.models

data class TrackDataModel(
    val cover: String,
    val coverUrl: String?,
    val dir: String,
    val duration: String,
    val hasLyrics: Boolean,
    val id: String,
    val isUserLikes: Boolean,
    val lyrics2: String?,
    val name: String,
    val parent: String,
    val peopleIds: List<String>,
    val price: String,
    val state: String,
    val year: String
)