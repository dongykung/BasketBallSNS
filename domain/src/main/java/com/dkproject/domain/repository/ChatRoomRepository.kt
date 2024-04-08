package com.dkproject.domain.repository

import com.dkproject.domain.model.chat.ChatRoom

interface ChatRoomRepository {
    suspend fun UploadChatRoom(myUid:String,chatRoom: ChatRoom) : String
    suspend fun getChatUidCheck(myUid:String,otherUid:String):String?
}