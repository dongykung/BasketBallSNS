package com.dkproject.domain.usecase.chat

import androidx.paging.PagingData
import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.domain.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class GetChatRoomsUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(myUid:String) : Flow<ChatRoom>  =chatRoomRepository.getChatRooms(myUid)

}
