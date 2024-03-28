package com.dkproject.data.model

data class ArticleDTO(
    val uid: String,
    val name: String,
    val imageList: List<String>,
    val price: String,
    val type: String,
    val content: String,
    val detailAddress: String,
    val lat: Double,
    val lng: Double
)
