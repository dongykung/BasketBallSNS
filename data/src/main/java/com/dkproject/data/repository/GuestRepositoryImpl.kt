package com.dkproject.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dkproject.data.model.GuestDTO
import com.dkproject.data.pagingDataSource.GuestDataSource
import com.dkproject.data.pagingDataSource.NearGuestDataSource
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.repository.GuestRepository
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject



// Repository 인터페이스의 구현체 정의
class GuestRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : GuestRepository {
    override suspend fun uploadGuest(guest: Guest): Resource<Boolean> {
        return try {
            Resource.Loading(true)
            val geohash=
                GeoFireUtils.getGeoHashForLocation(GeoLocation(guest.lat, guest.lng))
            val guestDto = GuestDTO(
                writeUid= guest.writeUid,
                uid=guest.uid,
                title=guest.title,
                positionList= guest.positionList,
                count=guest.count,
                content=guest.content,
                lat=guest.lat,
                lng=guest.lng,
                detailAddress = guest.detailAddress,
                detaildate =guest.detaildate,
                daydate = guest.daydate,
                geohash =geohash
            )
            firestore.collection("Guest").document(guest.uid).set(guestDto).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getGuest(position: String,date:Long): Flow<PagingData<Guest>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { GuestDataSource(firestore,position,date)}
        ).flow

    override suspend fun getNearGuest(position: String, date: Long): Flow<PagingData<Guest>>  =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NearGuestDataSource(firestore,position,date)}
        ).flow

    override suspend fun getGuestItem(uid: String): Flow<Guest> = callbackFlow {
        var eventsDocument : DocumentReference ?= null
        try {
            eventsDocument = firestore.collection("Guest").document(uid)
        }catch (e:Throwable){
            close(e)
        }

        val subscription = eventsDocument?.addSnapshotListener{ snapshot,error->
            if(snapshot==null) return@addSnapshotListener
            try {
                trySend(snapshot.toObject(GuestDTO::class.java)?.toDomainModel()!!)
            }catch (e:Throwable){

            }
        }

        awaitClose{ subscription?.remove()}
    }



    override suspend fun applyGuest(guestItemUid: String,userlist:List<String>) :Resource<Boolean>{
        return try {
            firestore.collection("Guest").document(guestItemUid).update("guestsUid",userlist).await()
            Resource.Success(true)
        }catch (e:Exception){
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun applyCancel(
        guestItemUid: String,
        updateList: List<String>
    ): Resource<Boolean> {
        return try {
            firestore.collection("Guest").document(guestItemUid).update("guestsUid",updateList).await()
            Resource.Success(true)
        }catch (e:Exception){
            Resource.Error(e.message.toString())
        }
    }
}

