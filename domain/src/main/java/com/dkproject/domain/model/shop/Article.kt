package com.dkproject.domain.model.shop

import com.sun.jndi.toolkit.url.Uri

data class Article(
    val name:String,
    val imageList:List<Uri>,
    val price:String,
    val type:String,
    val content:String,
    val detailAddress:String,
    val lat:Double,
    val lng:Double,
)





