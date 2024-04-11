package com.dkproject.domain.repository

import androidx.paging.PagingData
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.chat.ChatRoom
import kotlinx.coroutines.flow.Flow

interface ChatRoomRepository {
    suspend fun UploadChatRoom(myUid:String,chatRoom: ChatRoom) : String
    suspend fun getChatUidCheck(myUid:String,otherUid:String):String?

    suspend fun getChatRooms(userUid:String): Flow<ChatRoom>
}