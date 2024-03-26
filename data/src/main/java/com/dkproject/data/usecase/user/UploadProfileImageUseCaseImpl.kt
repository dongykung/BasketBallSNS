package com.dkproject.data.usecase.user

import android.util.Log
import androidx.core.net.toUri
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.usecase.user.SetUserInfoUseCase
import com.dkproject.domain.usecase.user.UploadProfileImageUseCase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadProfileImageUseCaseImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
) : UploadProfileImageUseCase {
    override suspend fun invoke(uid: String, photoUrl: String): String {
        val imageName = uid
        val metadata = storageMetadata { contentType = "image/jpg" }
        val profileImagestorageRef =
            firebaseStorage.reference.child("profileImage").child(uid).child("${imageName}.jpg")

        var upload = false
        profileImagestorageRef.putFile(photoUrl.toUri(), metadata).addOnSuccessListener {
            upload = true
        }.await()

        var imageUrl = ""
        if (upload)
            profileImagestorageRef.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
            }.await()

        if (imageUrl.isNotEmpty())
            return imageUrl
        else return "FAIL"
    }
}

