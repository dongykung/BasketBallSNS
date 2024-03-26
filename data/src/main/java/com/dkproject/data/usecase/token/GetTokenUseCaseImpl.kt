package com.dkproject.data.usecase.token

import com.dkproject.data.datastore.UserDataStore
import com.dkproject.domain.usecase.token.GetTokenUseCase
import javax.inject.Inject

class GetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) :GetTokenUseCase{
    override suspend fun invoke(): String? {
        return userDataStore.getToken()
    }
}