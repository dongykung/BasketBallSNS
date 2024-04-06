package com.dkproject.data.usecase.user

import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.usecase.user.SetUserInfoUseCase
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SetUserInfoUseCaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SetUserInfoUseCase {
    override suspend fun invoke(userInfo: UserInfo): Boolean {
        val userData = hashMapOf<String, Any>(
            "useruid" to userInfo.useruid,
            "nickname" to userInfo.nickname,
            "profileImageUrl" to userInfo.profileImageUrl,
            "playPosition" to userInfo.playPosition,
            "playStyle" to userInfo.playStyle,
            "userSkill" to userInfo.userSkill
        )
        return try {
            firestore.collection("users").document(userInfo.useruid)
                .set(userData, SetOptions.merge()).await()
            true
        } catch (e: FirebaseException) {
            false
        }
    }
}

