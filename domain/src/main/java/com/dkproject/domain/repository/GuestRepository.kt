package com.dkproject.domain.repository

import androidx.paging.PagingData
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest
import kotlinx.coroutines.flow.Flow

interface GuestRepository {
    suspend fun uploadGuest(guest: Guest): Resource<Boolean>

    suspend fun getGuest(position:String,date:Long):Flow<PagingData<Guest>>

    suspend fun getNearGuest(position:String,date:Long):Flow<PagingData<Guest>>

    suspend fun getGuestItem(uid:String): Flow<Guest>

    suspend fun applyGuest(guestItemUid:String,userlist:List<String>) :Resource<Boolean>
}