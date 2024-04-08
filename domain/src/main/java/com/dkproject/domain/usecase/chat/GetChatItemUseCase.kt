package com.dkproject.domain.usecase.chat

import com.dkproject.domain.model.chat.ChatMessage
import com.dkproject.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChatItemUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(uid:String):Flow<ChatMessage> = chatRepository.getChatMessages(uid)

}