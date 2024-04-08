package com.dkproject.domain.model.chat

data class ChatRoom(
    val chatRoomId:String,
    val lastMessage:String,
    val lastMessageTime:Long,
    var otherUserUid:String,
){
    fun toMap():Map<String,Any>{
        return mapOf(
           "chatRoomId" to chatRoomId,
            "lastMessage" to lastMessage,
            "lastMessageTime" to lastMessageTime,
            "otherUserUid" to otherUserUid
        )
    }
}