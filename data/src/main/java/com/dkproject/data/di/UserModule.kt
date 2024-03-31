package com.dkproject.data.di

import com.dkproject.data.usecase.location.GetLastLocationUseCaseImpl
import com.dkproject.data.usecase.login.CheckFirstUseCaseImpl
import com.dkproject.data.usecase.shop.GetArticleUseCaseImpl
import com.dkproject.data.usecase.shop.GetNearByArticleUseCaseImpl
import com.dkproject.data.usecase.shop.UploadArticleUseCaseImpl
import com.dkproject.data.usecase.token.ClearTokenUseCaseImpl
import com.dkproject.data.usecase.token.GetTokenUseCaseImpl
import com.dkproject.data.usecase.token.SetTokenUseCaseImpl
import com.dkproject.data.usecase.user.CheckNicknameUseCaseImpl
import com.dkproject.data.usecase.user.SetUserInfoUseCaseImpl
import com.dkproject.data.usecase.user.UploadProfileImageUseCaseImpl
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.dkproject.domain.usecase.login.CheckFirstUseCase
import com.dkproject.domain.usecase.shop.GetArticleUseCase
import com.dkproject.domain.usecase.shop.GetNearByArticleUseCase
import com.dkproject.domain.usecase.shop.UploadArticleUseCase
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.token.SetTokenUseCase
import com.dkproject.domain.usecase.user.CheckNicknameUseCase
import com.dkproject.domain.usecase.user.SetUserInfoUseCase
import com.dkproject.domain.usecase.user.UploadProfileImageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindSetTokenUseCase(uc: SetTokenUseCaseImpl): SetTokenUseCase

    @Binds
    abstract fun bindCheckFirstUserUseCase(uc: CheckFirstUseCaseImpl): CheckFirstUseCase

    @Binds
    abstract fun bindGetTokenUseCase(uc: GetTokenUseCaseImpl): GetTokenUseCase

    @Binds
    abstract fun bindClearTokenUseCase(uc: ClearTokenUseCaseImpl): ClearTokenUseCase

    @Binds
    abstract fun bindCheckNicknameUseCase(uc: CheckNicknameUseCaseImpl): CheckNicknameUseCase

    @Binds
    abstract fun bindSetUserInfoUseCase(uc: SetUserInfoUseCaseImpl): SetUserInfoUseCase

    @Binds
    abstract fun bindUploadProfileImageUseCase(uc: UploadProfileImageUseCaseImpl): UploadProfileImageUseCase

    @Binds
    abstract fun bindGetLastLocationUseCase(uc: GetLastLocationUseCaseImpl): GetLastLocationUseCase

    @Binds
    abstract fun bindUploadArticleUseCase(uc: UploadArticleUseCaseImpl): UploadArticleUseCase

    @Binds
    abstract fun bindGetArticleUseCase(uc: GetArticleUseCaseImpl): GetArticleUseCase

    @Binds
    abstract fun bindGetNeartByArticleUseCase(uc: GetNearByArticleUseCaseImpl): GetNearByArticleUseCase

}