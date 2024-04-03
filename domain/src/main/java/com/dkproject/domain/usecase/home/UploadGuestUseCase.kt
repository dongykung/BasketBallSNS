package com.dkproject.domain.usecase.home

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UploadGuestUseCase(private val guestRepository:GuestRepository) {
    suspend operator fun invoke(guest: Guest): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(true))
            emit(guestRepository.uploadGuest(guest))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}