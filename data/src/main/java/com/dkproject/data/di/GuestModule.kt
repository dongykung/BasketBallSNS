package com.dkproject.data.di

import com.dkproject.data.repository.GuestRepositoryImpl
import com.dkproject.data.repository.UserRepositoryImpl
import com.dkproject.domain.repository.GuestRepository
import com.dkproject.domain.repository.UserRepository
import com.dkproject.domain.usecase.home.GetGuestItemUseCase
import com.dkproject.domain.usecase.home.GetGuestUseCase
import com.dkproject.domain.usecase.home.GetNearGuestUseCase
import com.dkproject.domain.usecase.home.UploadGuestUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
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
    @Provides
    @Singleton
    fun provideloadNeqrGuestUseCase(guestTestRepository: GuestRepository):GetNearGuestUseCase{
        return GetNearGuestUseCase(guestTestRepository)
    }

    @Provides
    @Singleton
    fun provideloadGuestItemUseCase(guestTestRepository: GuestRepository):GetGuestItemUseCase{
        return GetGuestItemUseCase(guestTestRepository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore):UserRepository{
        return UserRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserUseCase(userRepositoryImpl: UserRepositoryImpl):GetUserInfoUseCase{
        return GetUserInfoUseCase(userRepositoryImpl)
    }

}