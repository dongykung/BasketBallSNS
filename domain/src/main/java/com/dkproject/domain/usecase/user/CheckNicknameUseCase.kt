package com.dkproject.domain.usecase.user

interface CheckNicknameUseCase {
    operator suspend fun invoke(nickname:String):Boolean
}