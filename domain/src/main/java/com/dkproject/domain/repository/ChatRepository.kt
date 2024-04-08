package com.dkproject.domain.repository

import com.dkproject.domain.model.chat.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatMessages(roomUid:String): Flow<ChatMessage>

    suspend fun uploadChat(roomUid: String,message:ChatMessage):Boolean
}