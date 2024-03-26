package com.dkproject.domain.usecase.token

interface ClearTokenUseCase {
    operator suspend fun invoke():Result<Unit>
}