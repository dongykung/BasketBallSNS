package com.dkproject.data.usecase.shop

import android.util.Log
import com.dkproject.data.model.ArticleDTO
import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.usecase.shop.UploadArticleUseCase
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UploadArticleUseCaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UploadArticleUseCase {
    override suspend fun invoke(article: Articles): Boolean {
        var returnvalue = false
        val geohash=
            GeoFireUtils.getGeoHashForLocation(GeoLocation(article.lat, article.lng))
        Log.d("GeoHash", geohash.toString())
        val updateData = ArticleDTO(
            writerUid = article.writerUid,
            uid = article.uid,
            name = article.name,
            imageList = article.imageList,
            price = article.price,
            type = article.type,
            content = article.content,
            detailAddress = article.detailAddress,
            lat = article.lat,
            lng = article.lng,
            geohash = geohash
        )
        try {
            firestore.collection("Article").document(article.uid).set(updateData)
                .addOnSuccessListener {
                    returnvalue = true
                }.addOnFailureListener {
                returnvalue = false
            }.await()
            return returnvalue
        } catch (e: Exception) {
            return returnvalue
        }

    }
}