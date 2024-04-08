package com.dkproject.data.di

import com.dkproject.data.repository.ChatRepositoryImpl
import com.dkproject.data.repository.ChatRoomRepositoryImpl
import com.dkproject.domain.repository.ChatRepository
import com.dkproject.domain.repository.ChatRoomRepository
import com.dkproject.domain.usecase.chat.GetChatItemUseCase
import com.dkproject.domain.usecase.chat.GetChatUidCheckUseCase
import com.dkproject.domain.usecase.chat.GetUpdateChatRoomUseCase
import com.dkproject.domain.usecase.chat.UploadChatUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    @Singleton
    fun provideChatRoomRepository(firebaseDatabase: FirebaseDatabase): ChatRoomRepository {
        return ChatRoomRepositoryImpl(firebaseDatabase)
    }
    @Provides
    @Singleton
    fun provideGetUpdateChatRoomUseCase(chatRoomRepository: ChatRoomRepository):GetUpdateChatRoomUseCase {
        return GetUpdateChatRoomUseCase(chatRoomRepository)
    }

    @Provides
    @Singleton
    fun provideChatMessageRepository(firebaseDatabase: FirebaseDatabase): ChatRepository {
        return ChatRepositoryImpl(firebaseDatabase)
    }
    @Provides
    @Singleton
    fun provideUploadChatUseCase(chatRepository: ChatRepository):UploadChatUseCase {
        return UploadChatUseCase(chatRepository)
    }

    @Provides
    @Singleton
    fun provideCheckChatUidUseCase(chatRoomRepository: ChatRoomRepository):GetChatUidCheckUseCase {
        return GetChatUidCheckUseCase(chatRoomRepository)
    }
    @Provides
    @Singleton
    fun provideGetChatItemUseCase(chatRepository: ChatRepository):GetChatItemUseCase {
        return GetChatItemUseCase(chatRepository)
    }
}