package com.dkproject.domain.usecase.token

interface SetTokenUseCase {
    operator suspend fun invoke(token:String)
}