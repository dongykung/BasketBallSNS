package com.dkproject.domain.model.shop



data class Articles(
    val writerUid:String,
    val uid:String,
    val name:String,
    val imageList:List<String>,
    val price:String,
    val type:String,
    val content:String,
    val detailAddress:String,
    val lat:Double,
    val lng:Double,
)






