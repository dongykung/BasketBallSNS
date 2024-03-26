package com.dkproject.data.usecase.token

import com.dkproject.data.datastore.UserDataStore
import com.dkproject.domain.usecase.token.SetTokenUseCase
import javax.inject.Inject

class SetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : SetTokenUseCase {
    override suspend fun invoke(token: String) {
        userDataStore.setToken(token)
    }
}