package com.dkproject.data.usecase.user

import android.util.Log
import com.dkproject.domain.usecase.user.CheckNicknameUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckNicknameUseCaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CheckNicknameUseCase {
    override suspend fun invoke(nickname: String): Boolean {
        val document =
            firestore.collection("users").whereEqualTo("nickname", nickname).get().await()
//        Log.e("equal test", document.documents.toString())
//        Log.e("equal test", document.documents.size.toString())
//        Log.e("equal test", document.toString())
        //0일 시 사용가능한 닉네임 = 반환 true
        //0이 아닐 시 존재하는 닉네임 = 반환 false
        return document.documents.size == 0
    }
}