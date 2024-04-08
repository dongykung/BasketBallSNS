package com.dkproject.data.model

data class ChatRoomDTO (
    val chatRoomId:String="",
    val lastMessage:String="",
    val lastMessageTime:Long=System.currentTimeMillis(),
    val otherUserUid:String="",
)