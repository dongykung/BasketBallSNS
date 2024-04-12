package com.dkproject.domain.usecase.home

import com.dkproject.domain.common.Resource
import com.dkproject.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApplyGuestUseCase(private val guestRepository: GuestRepository) {
    suspend operator fun invoke(guestItemUid:String,userlist:List<String>) :Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            emit(guestRepository.applyGuest(guestItemUid, userlist))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

}