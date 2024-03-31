package com.dkproject.domain.usecase.shop

import androidx.paging.PagingData
import com.dkproject.domain.model.shop.SimpleArticle
import kotlinx.coroutines.flow.Flow

interface GetArticleUseCase {
    suspend operator fun invoke(category:String,sort:Boolean,sortbydis:Boolean):Result<Flow<PagingData<SimpleArticle>>>
}