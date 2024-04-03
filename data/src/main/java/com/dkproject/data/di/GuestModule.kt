package com.dkproject.data.di

import com.dkproject.data.repository.GuestRepositoryImpl
import com.dkproject.domain.repository.GuestRepository
import com.dkproject.domain.usecase.home.GetGuestUseCase
import com.dkproject.domain.usecase.home.UploadGuestUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton





@Module
@InstallIn(SingletonComponent::class)
object GuestModule2{
    @Provides
    @Singleton
    fun providetestguest(firestore: FirebaseFirestore): GuestRepository {
        return GuestRepositoryImpl(firestore)
    }
    @Provides
    @Singleton
    fun provideUploadGuestUseCase(guestTestRepository: GuestRepository):UploadGuestUseCase{
        return UploadGuestUseCase(guestTestRepository)
    }
    @Provides
    @Singleton
    fun provideloadGuestUseCase(guestTestRepository: GuestRepository):GetGuestUseCase{
        return GetGuestUseCase(guestTestRepository)
    }

}