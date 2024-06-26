package com.dkproject.data.pagingDataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dkproject.data.model.ArticleDTO
import com.dkproject.data.model.GuestDTO
import com.dkproject.domain.model.home.Guest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class GuestDataSource(
    val firestore: FirebaseFirestore,
    val category:String,
    val date:Long
) : PagingSource<QuerySnapshot,Guest>() {
    companion object{
        const val TAG="GuestDataSource"
    }
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Guest>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Guest> {
        try {
            Log.d(TAG, category)

            val currentPage = if(category.equals("모두보기")&& date.toInt() ==0) params.key ?: firestore.collection("Guest")
                .whereGreaterThan("daydate",System.currentTimeMillis())
                .limit(10).get().await()
            else if(category.equals("모두보기")&&date.toInt()!=0) params.key ?:
            firestore.collection("Guest")
                .whereEqualTo("daydate",date).limit(10).get().await()
            else if(!category.equals("모두보기")&&date.toInt()==0) params.key ?:
            firestore.collection("Guest").whereArrayContains("positionList",category)
                .whereGreaterThan("daydate",System.currentTimeMillis()).limit(10).get().await()
                else params.key ?:
                firestore.collection("Guest").whereArrayContains("positionList",category)
                    .whereEqualTo("daydate",date).limit(10).get().await()


            val data = currentPage.documents.map {
                it.toObject(GuestDTO::class.java)!!
            }
            val formatData = data.map {
                 it.toDomainModel()
            }
              .sortedBy { it.detaildate }


            val lastVisibleArticle = currentPage.documents[currentPage.size() - 1]

            val nextPage = if(category.equals("모두보기")&& date.toInt() ==0) params.key ?: firestore.collection("Guest")
                .whereGreaterThan("daydate",System.currentTimeMillis())
                .startAfter(lastVisibleArticle).limit(10).get().await()
            else if(category.equals("모두보기")&&date.toInt()!=0) params.key ?:
            firestore.collection("Guest")
                .whereEqualTo("daydate",date).startAfter(lastVisibleArticle).limit(10).get().await()
            else if(!category.equals("모두보기")&&date.toInt()==0) params.key ?:
            firestore.collection("Guest").whereArrayContains("positionList",category)
                .whereGreaterThan("daydate",System.currentTimeMillis()).startAfter(lastVisibleArticle).limit(10).get().await()
            else params.key ?:
            firestore.collection("Guest").whereArrayContains("positionList",category)
                .whereEqualTo("daydate",date).startAfter(lastVisibleArticle).limit(10).get().await()


            return LoadResult.Page(
                data = formatData,
                prevKey = null,
                nextKey = nextPage
            )
        }catch (e:Exception){
            Log.d(TAG, e.message.toString())
            return LoadResult.Error(e)
        }
    }
}