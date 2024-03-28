package com.dkproject.data.usecase.file

import androidx.core.net.toUri
import com.dkproject.domain.usecase.file.UploadImageListUseCase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class UploadImageListUseCaseImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
):UploadImageListUseCase{
    override suspend operator fun invoke(uid: String, itemId:String, photoUrl: String): String? {
        val imageName = UUID.randomUUID().toString()
        val metadata = storageMetadata { contentType = "image/jpg" }
        val profileImagestorageRef =
            firebaseStorage.reference.child("ShopImages").child(uid).child(itemId).child("${imageName}.jpg")

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
        else return null
    }
}