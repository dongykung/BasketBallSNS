package com.dkproject.data.usecase.shop

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.log
import com.dkproject.data.model.ArticleDTO
import com.dkproject.domain.model.shop.SimpleArticle
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ShopPagingSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) : PagingSource<QuerySnapshot, SimpleArticle>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, SimpleArticle>): QuerySnapshot ?=null

    var category: String = ""
    var sortByPrice: Boolean = false
    var sortByDistance:Boolean=false
    fun setParams(category: String, sortByPrice: Boolean,sortByDistance:Boolean) {
        this.category = category
        this.sortByPrice = sortByPrice
        this.sortByDistance = sortByDistance
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, SimpleArticle> {
        try {

            Log.d("category",category)
            val currentPage = if(category.equals("모두보기")) params.key ?: firestore.collection("Article")
               .limit(10).get().await()
            else params.key ?:
                firestore.collection("Article")
                    .whereEqualTo("type",category).limit(10).get().await()



            val data = currentPage.documents.map {
                it.toObject(ArticleDTO::class.java)
            }

            val formatdata = if(!sortByPrice)
                data.map {
                it?.toSimpleArticle()!!
            }.sortedByDescending { it.price }
            else
                data.map {
                    it?.toSimpleArticle()!!
                }.sortedBy { it.price }



            val lastVisibleArticle = currentPage.documents[currentPage.size() - 1]
            val nextPage = if(category.equals("모두보기")) firestore.collection("Article")
              .limit(10).startAfter(lastVisibleArticle).get().await()
            else
            firestore.collection("Article")
                .whereEqualTo("type",category)
             .limit(10).startAfter(lastVisibleArticle).get().await()


            return LoadResult.Page(
                data = formatdata,
                prevKey = null,
                nextKey = nextPage
            )
        }catch (e:Exception){
            Log.d("FailLoad", e.message.toString())
            return LoadResult.Error(e)
        }
    }
}