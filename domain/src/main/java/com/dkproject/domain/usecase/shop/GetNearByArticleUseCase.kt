package com.dkproject.domain.usecase.shop

import androidx.paging.PagingData
import com.dkproject.domain.model.shop.SimpleArticle
import kotlinx.coroutines.flow.Flow

interface GetNearByArticleUseCase {
    suspend operator fun invoke(category:String,sort:Boolean,change:Boolean):Result<Flow<PagingData<SimpleArticle>>>
}