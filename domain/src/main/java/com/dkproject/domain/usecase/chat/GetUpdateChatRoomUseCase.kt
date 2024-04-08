package com.dkproject.domain.usecase.chat

import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.domain.repository.ChatRoomRepository

class GetUpdateChatRoomUseCase(
    private val chatRoomRepository: ChatRoomRepository
) {
    operator suspend fun invoke(myUid:String,chatRoom: ChatRoom) : Result<String> = runCatching{
        chatRoomRepository.UploadChatRoom(myUid,chatRoom)
    }
}