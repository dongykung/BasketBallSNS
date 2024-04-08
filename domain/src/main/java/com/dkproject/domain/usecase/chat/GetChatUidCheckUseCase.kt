package com.dkproject.domain.usecase.chat

import com.dkproject.domain.repository.ChatRoomRepository

class GetChatUidCheckUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(myuid:String,otherUid:String):String{
        return chatRoomRepository.getChatUidCheck(myuid,otherUid) ?:""
    }
}