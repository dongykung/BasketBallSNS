package com.dkproject.data.usecase.shop

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dkproject.domain.model.shop.SimpleArticle
import com.dkproject.domain.usecase.shop.GetArticleUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider

class GetArticleUseCaseImpl @Inject constructor(
   private val firestore: FirebaseFirestore
) :GetArticleUseCase{
    override suspend fun invoke(category:String,sort:Boolean,sortbydis:Boolean): Result<Flow<PagingData<SimpleArticle>>> = runCatching {
        val pagingSource = ShopPagingSource(firestore)
        pagingSource.setParams(category, sort,sortbydis)
        Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                pagingSource
            }
        ).flow
    }

}