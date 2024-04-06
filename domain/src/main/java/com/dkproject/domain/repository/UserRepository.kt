package com.dkproject.domain.repository

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo

interface UserRepository {
    suspend fun getUserInfo(uid:String):Resource<UserInfo>
}