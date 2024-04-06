package com.dkproject.data.repository


import android.util.Log
import com.dkproject.data.model.UserDTO
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
):UserRepository {
    override suspend fun getUserInfo(uid:String): Resource<UserInfo> {
        return try {
          val test =  firestore.collection("users").document(uid).get().await()
            val data = test.toObject(UserDTO::class.java)
           val result = data?.toDomainModel() ?: UserInfo("","","", emptyList(), emptyList(),"")
            Resource.Success(result)
        } catch (e: Exception) {
         Resource.Error(e.message.toString())
        }
    }
}
