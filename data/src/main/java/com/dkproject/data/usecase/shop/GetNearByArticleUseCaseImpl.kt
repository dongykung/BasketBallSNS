package com.dkproject.data.usecase.shop

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dkproject.domain.model.shop.SimpleArticle
import com.dkproject.domain.usecase.shop.GetNearByArticleUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearByArticleUseCaseImpl  @Inject constructor(
    private val firestore: FirebaseFirestore,
):GetNearByArticleUseCase{

    override suspend fun invoke(
        category: String,
        sort: Boolean,
        change:Boolean
    ): Result<Flow<PagingData<SimpleArticle>>> = runCatching {
        val pagingSource = NearByShopPagingSource(firestore)
        pagingSource.setParams(category,sort,change)
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