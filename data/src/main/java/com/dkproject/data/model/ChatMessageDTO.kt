package com.dkproject.data.model

import com.dkproject.domain.model.chat.ChatMessage

data class ChatMessageDTO(
    var chatId:String="",
    val message:String="",
    val time:Long = System.currentTimeMillis(),
    val userUid:String="",
){
    fun toDimainModel():ChatMessage{
        return ChatMessage(
            chatId=chatId,
            message=message,
            time=time,
            userUid=userUid
        )
    }
}