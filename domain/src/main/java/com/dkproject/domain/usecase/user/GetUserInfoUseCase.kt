package com.dkproject.domain.usecase.user

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserInfoUseCase(private val userRepository: UserRepository) {

   suspend operator fun invoke(uid:String) : Flow<Resource<UserInfo>> = flow{
      try {
          emit(Resource.Loading())
          emit(userRepository.getUserInfo(uid))
      }catch (e:Exception){
          emit(Resource.Error(e.message.toString()))
      }
   }
}