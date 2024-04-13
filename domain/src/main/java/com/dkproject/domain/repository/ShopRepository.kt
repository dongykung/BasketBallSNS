package com.dkproject.domain.repository

import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.shop.Articles

interface ShopRepository {
    suspend fun getArticleInfo(uid:String):Resource<Articles>

    suspend fun deleteArticle(uid:String):Boolean
}