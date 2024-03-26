package com.dkproject.domain.usecase.user

import com.dkproject.domain.model.UserInfo

interface SetUserInfoUseCase {
    operator suspend fun invoke(userInfo:UserInfo):Boolean
}