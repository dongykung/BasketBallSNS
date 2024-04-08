package com.dkproject.domain.usecase.chat

import com.dkproject.domain.model.chat.ChatMessage
import com.dkproject.domain.repository.ChatRepository

class UploadChatUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(roomUid:String,message:ChatMessage):Result<Boolean> = runCatching {
        chatRepository.uploadChat(roomUid = roomUid, message = message)
    }
}