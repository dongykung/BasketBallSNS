package com.dkproject.domain.usecase.shop

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetArticleItemUseCase(private val shopRepository: ShopRepository) {
    suspend operator fun invoke(uid:String) :Flow<Resource<Articles>> = flow {
       try {
           emit(Resource.Loading())
           emit(shopRepository.getArticleInfo(uid))
       }catch (e:Exception){
           emit(Resource.Error(e.message.toString()))
       }
    }
}