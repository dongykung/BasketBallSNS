package com.dkproject.data.di

import com.dkproject.data.usecase.file.UploadImageListUseCaseImpl
import com.dkproject.domain.usecase.file.UploadImageListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class FileModule {

    @Binds
    abstract fun bindUploadImageListUseCase(uc:UploadImageListUseCaseImpl) : UploadImageListUseCase
}