package com.dkproject.data.di

import com.dkproject.data.repository.ShopRepositoryImpl
import com.dkproject.domain.repository.ChatRepository
import com.dkproject.domain.repository.ShopRepository
import com.dkproject.domain.usecase.chat.UploadChatUseCase
import com.dkproject.domain.usecase.shop.GetArticleItemUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ShopModule {

    @Provides
    @Singleton
    fun provideshopRepository(firestore: FirebaseFirestore): ShopRepository {
        return ShopRepositoryImpl(firestore)
    }
    @Provides
    @Singleton
    fun providegetArticleItemUseCase(shopRepository: ShopRepository): GetArticleItemUseCase {
        return GetArticleItemUseCase(shopRepository)
    }
}