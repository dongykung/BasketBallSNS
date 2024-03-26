package com.dkproject.domain.usecase.token

interface GetTokenUseCase {
    operator suspend fun invoke():String?
}