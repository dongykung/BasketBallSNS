package com.dkproject.data.usecase.shop

import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.usecase.shop.UploadArticleUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadArticleUseCaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) :UploadArticleUseCase{
    override suspend fun invoke(article:Articles): Boolean {
        var returnvalue =false
        try {
            firestore.collection("Article").document(article.uid).set(article).
            addOnSuccessListener {
                returnvalue=true
            }.addOnFailureListener{
                returnvalue=false
            }.await()
            return returnvalue
        }catch (e:Exception){
            return returnvalue
        }

    }
}