package com.dkproject.domain.usecase.home

import androidx.paging.PagingData
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow

class GetNearGuestUseCase(private val guestRepository: GuestRepository) {
    suspend operator fun invoke(position:String,date:Long): Flow<PagingData<Guest>> =
        guestRepository.getNearGuest(position,date)
}