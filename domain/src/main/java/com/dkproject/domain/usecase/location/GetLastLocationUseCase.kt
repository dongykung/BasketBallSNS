package com.dkproject.domain.usecase.location

import com.dkproject.domain.model.MyLocation
import kotlinx.coroutines.flow.Flow

interface GetLastLocationUseCase {
     fun requestCurrentLocation():Flow<MyLocation>
}