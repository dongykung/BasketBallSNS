package com.dkproject.data.usecase.shop

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dkproject.data.model.ArticleDTO
import com.dkproject.domain.model.shop.SimpleArticle
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NearByShopPagingSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val getLastLocationUseCase: GetLastLocationUseCase,
    val category: String ,
    val sortByPrice: Boolean ,
    val change:Boolean
) :PagingSource<QuerySnapshot,SimpleArticle>(){


    override fun getRefreshKey(state: PagingState<QuerySnapshot, SimpleArticle>): QuerySnapshot?=null



    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, SimpleArticle> {
        try {
//            var lat :Double =0.0
//            var lng : Double = 0.0
//            getLastLocationUseCase.requestCurrentLocation().collect{
//                lat = it.Lat
//                lng = it.Lng
//            }
            val center = GeoLocation(37.3774437,126.98378539999999)
            val radius = 5.0*1000.0
            val bounds  = GeoFireUtils.getGeoHashQueryBounds(center,radius)
            val tasks: MutableList<QuerySnapshot> = ArrayList()
            var matchGroup: MutableList<SimpleArticle> = ArrayList()
            for(b in bounds){
                if(category.equals("모두보기")) {
                    val q = firestore.collection("Article")
                        .orderBy("geohash")
                        .startAfter(b.startHash)
                        .endAt(b.endHash)
                        .get().await()
                    tasks.add(q)
                }else{
                    val q = firestore.collection("Article")
                        .whereEqualTo("type",category)
                        .orderBy("geohash")
                        .startAfter(b.startHash)
                        .endAt(b.endHash)
                        .get().await()
                    tasks.add(q)
                }
            }

                for(task in tasks){
                    val snap = task.documents
                    for(doc in snap){
                        val lat = doc.getDouble("lat")!!
                        val lng = doc.getDouble("lng")!!
                        val docLocation = GeoLocation(lat,lng)
                        val distanceInM = GeoFireUtils.getDistanceBetween(docLocation,center)
                        if(distanceInM<=radius){
                            val article = doc.toObject(ArticleDTO::class.java)?.toSimpleArticle()
                            if(article!=null)
                                matchGroup.add(article)
                        }
                    }
                }


            if(!sortByPrice){
                matchGroup = matchGroup.sortedByDescending { it.price }.toMutableList()
            }else{
                matchGroup = matchGroup.sortedBy { it.price }.toMutableList()
            }

            return LoadResult.Page(
                data = matchGroup,
                prevKey = null,
                nextKey = null
            )
        }catch (e:Exception){
            return LoadResult.Error(e)
        }
    }
}