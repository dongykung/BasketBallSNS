package com.dkproject.data.model

import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.model.shop.SimpleArticle
import com.google.firebase.firestore.GeoPoint

data class ArticleDTO(
    val writerUid:String="",
    val uid: String="",
    val name: String="",
    val imageList: List<String> = emptyList(),
    val price: Int = 1,
    val type: String = "",
    val content: String = "",
    val detailAddress: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val geohash:String ?=null
){
    fun toSimpleArticle():SimpleArticle{
        return SimpleArticle(
            writerUid = writerUid,
            name = name,
            uid = uid,
            price = price,
            detailAddress = detailAddress,
            image = imageList.get(0),
            type=type
        )
    }

    fun toDomainModel():Articles{
        return Articles(
            writerUid=writerUid,
         uid=uid,
         name=name,
         imageList=imageList,
         price=price,
         type=type,
         content=content,
         detailAddress=detailAddress,
         lat=lat,
         lng=lng,
        )
    }
}

