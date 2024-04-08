package com.dkproject.domain.model.home


data class Guest(
    val writeUid:String,
    val uid:String,
    val title:String,
    val positionList:List<String>,
    val count:Int,
    val content:String,
    val lat:Double,
    val lng:Double,
    val guestsUid:List<String>,
    val detailAddress:String,
    val detaildate:Long,
    val daydate:Long,
)






