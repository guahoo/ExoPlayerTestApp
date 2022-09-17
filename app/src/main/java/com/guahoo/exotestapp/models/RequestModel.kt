package com.guahoo.exotestapp.models

data class RequestModel(
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val results: List<AlbumDataModel>,
    val status: String
)