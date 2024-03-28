package com.dkproject.domain.usecase.file

interface UploadImageListUseCase {
    suspend operator fun invoke(uid: String, itemId:String, photoUrl: String):String?
}