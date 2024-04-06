package com.dkproject.data.pagingDataSource

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dkproject.data.model.ArticleDTO
import com.dkproject.data.model.GuestDTO
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.model.shop.SimpleArticle
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await

class NearGuestDataSource(
    val firestore: FirebaseFirestore,
    val category: String,
    val date: Long
) : PagingSource<QuerySnapshot, Guest>() {
    companion object {
        const val TAG = "NearGuestDataSource"
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Guest>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Guest> {
        try {
            //            var lat :Double =0.0
//            var lng : Double = 0.0
//            getLastLocationUseCase.requestCurrentLocation().collect{
//                lat = it.Lat
//                lng = it.Lng
            //}
            val center = GeoLocation(37.3774437,126.98378539999999)
            val radius = 5.0*1000.0
            val bounds  = GeoFireUtils.getGeoHashQueryBounds(center,radius)
            val tasks: MutableList<QuerySnapshot> = ArrayList()
            var matchGroup: MutableList<Guest> = ArrayList()
            for(b in bounds){
                if(category.equals("모두보기")&& date.toInt() ==0) {
                    val q = firestore.collection("Guest")
                        .whereGreaterThan("daydate", System.currentTimeMillis())
                        .orderBy("geohash")
                        .startAfter(b.startHash)
                        .endAt(b.endHash)
                        .get().await()
                    tasks.add(q)
                }
                else if(category.equals("모두보기")&&date.toInt()!=0) {
                    val q= firestore.collection("Guest")
                        .whereEqualTo("daydate", date)
                        .orderBy("geohash").
                        startAfter(b.startHash).
                        endAt(b.endHash).get().await()
                    tasks.add(q)
                }
                else if(!category.equals("모두보기")&&date.toInt()==0) {
                    val q = firestore.collection("Guest").
                        whereArrayContains("positionList", category)
                        .whereGreaterThan("daydate", System.currentTimeMillis())
                        .orderBy("geohash").
                        startAfter(b.startHash).
                        endAt(b.endHash).get()
                        .await()
                    tasks.add(q)
                }
                else {
                    val q = firestore.collection("Guest")
                        .whereArrayContains("positionList", category)
                        .whereEqualTo("daydate", date)
                        .orderBy("geohash")
                        .startAfter(b.startHash)
                        .endAt(b.endHash).get().await()
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
                        val guest = doc.toObject(GuestDTO::class.java)
                        if(guest!=null){
                            matchGroup.add(guest.toDomainModel())
                        }
                    }
                }
            }
            matchGroup = matchGroup.sortedBy { it.detaildate }.toMutableList()
            return LoadResult.Page(
                data = matchGroup,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            Log.d(GuestDataSource.TAG, e.message.toString())
            return LoadResult.Error(e)
        }
    }
}