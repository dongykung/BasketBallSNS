package com.dkproject.domain.usecase.login

interface CheckFirstUseCase {
    operator suspend fun invoke(uid:String):Boolean
}