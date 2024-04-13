package com.dkproject.domain.usecase.home

import com.dkproject.domain.common.Resource
import com.dkproject.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApplyCancelUseCase(private val guestRepository: GuestRepository) {
    suspend operator fun invoke(guestItemUid:String,updateList:List<String>) : Flow<Resource<Boolean>> = flow {
       try {
           emit(Resource.Loading())
           emit(guestRepository.applyCancel(guestItemUid,updateList))
       }catch (e:Exception){
           emit(Resource.Error(e.message.toString()))
       }
    }
}