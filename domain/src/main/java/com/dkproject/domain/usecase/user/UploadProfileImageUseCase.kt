package com.dkproject.domain.usecase.user

interface UploadProfileImageUseCase {
    operator suspend fun invoke(uid:String,photoUrl:String):String
}