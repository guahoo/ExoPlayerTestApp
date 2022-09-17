package com.guahoo.exotestapp.models

data class AlbumDataModel(
    val cover: String?,
    val coverUrl: String?,
    val dir: String?,
    val duration: Int?,
    val id: String?,
    val isUserLikes: Boolean?,
    val name: String?,
    val parent: Any,
    val peopleIds: List<String>?,
    val price: String?,
    val state: String?,
    val trackCount: Int?,
    val year: String?
)