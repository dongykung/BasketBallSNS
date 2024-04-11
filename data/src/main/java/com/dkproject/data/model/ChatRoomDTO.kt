package com.dkproject.data.model

import com.dkproject.domain.model.chat.ChatRoom

data class ChatRoomDTO (
    val chatRoomId:String="",
    val lastMessage:String="",
    val lastMessageTime:Long=System.currentTimeMillis(),
    val otherUserUid:String="",
){
    fun toDomainModel():ChatRoom{
        return ChatRoom(
            chatRoomId=chatRoomId,
         lastMessage=lastMessage,
         lastMessageTime=lastMessageTime,
         otherUserUid=otherUserUid,
        )
    }
}