package com.dkproject.presentation.model

import com.dkproject.domain.model.shop.SimpleArticle

data class ShopUiModel(
    val writerUid: String,
    val image:String,
    val name:String,
    val uid:String,
    val price:Int,
    val type:String,
    val detailAddress: String
)



fun SimpleArticle.toUiModel():ShopUiModel{
    return ShopUiModel(
        writerUid=writerUid,
        image=image,
        name=name,
        uid=uid,
        price=price,
        type=this.type,
        detailAddress=detailAddress
    )
}