package com.dkproject.data.usecase.token

import com.dkproject.data.datastore.UserDataStore
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import javax.inject.Inject

class ClearTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) :ClearTokenUseCase{
    override suspend fun invoke(): Result<Unit> = runCatching {
        userDataStore.clear()
    }
}