package com.dkproject.data.usecase.login

import android.util.Log
import com.dkproject.domain.usecase.login.CheckFirstUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckFirstUseCaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
):CheckFirstUseCase {
    override suspend fun invoke(uid:String): Boolean {
        var returnValue=false
        val documentSnapshot = firestore.collection("users").document(uid).get().await()
        Log.e("Tst", documentSnapshot.data.toString())
        return documentSnapshot.data == null
    }
}