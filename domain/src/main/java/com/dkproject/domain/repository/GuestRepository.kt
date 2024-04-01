package com.dkproject.domain.repository

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest

interface GuestRepository {
    suspend fun uploadGuest(guest: Guest): Resource<Boolean>
}