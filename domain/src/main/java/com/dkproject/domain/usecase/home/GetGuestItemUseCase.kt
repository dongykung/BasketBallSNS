package com.dkproject.domain.usecase.home

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGuestItemUseCase(private val guestRepository: GuestRepository){
    suspend operator fun invoke(uid:String) : Flow<Resource<Guest>> = flow {
        try {
            emit(Resource.Loading())
            emit(guestRepository.getGuestItem(uid))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }

    }
}