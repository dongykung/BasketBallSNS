package com.dkproject.data.repository

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.repository.GuestRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject



// Repository 인터페이스의 구현체 정의
class GuestRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : GuestRepository {
    override suspend fun uploadGuest(guest: Guest): Resource<Boolean> {
        return try {
            firestore.collection("Guest").document(guest.uid).set(guest).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}