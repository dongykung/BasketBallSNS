package com.dkproject.data.repository

import android.util.Log
import com.dkproject.data.model.ArticleDTO
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.repository.ShopRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
   private val firestore: FirebaseFirestore
):ShopRepository {
    override suspend fun getArticleInfo(uid:String): Resource<Articles> {
       return try {
           Resource.Loading(null)
           val snapshot = firestore.collection("Article").document(uid).get().await()
           if(snapshot!=null){
               val data = snapshot.toObject(ArticleDTO::class.java)?.toDomainModel()!!
               Resource.Success(data)
           }else{//글이 삭제됨
               Log.d("ShopRepositoryImpl","dont exist")
               Resource.Error("존재하지 않는 글입니다")
           }
        }catch (e:Exception){
           Log.d("ShopRepositoryImpl",e.message.toString())
            Resource.Error(e.message.toString())
        }

    }

    override suspend fun deleteArticle(uid: String):Boolean {
        return try {
            firestore.collection("Article").document(uid).delete().await()
            return true
        }catch (e:Exception){
            return false
        }
    }
}