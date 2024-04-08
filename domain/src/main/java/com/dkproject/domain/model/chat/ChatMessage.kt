package com.dkproject.domain.model.chat

data class ChatMessage(
    var chatId:String,
    val message:String,
    val time:Long,
    val userUid:String,
)