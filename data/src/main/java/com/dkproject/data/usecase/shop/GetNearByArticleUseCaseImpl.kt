package com.dkproject.data.usecase.shop

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dkproject.domain.model.shop.SimpleArticle
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.dkproject.domain.usecase.shop.GetNearByArticleUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearByArticleUseCaseImpl  @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val getLastLocationUseCase: GetLastLocationUseCase
):GetNearByArticleUseCase{

    override suspend fun invoke(
        category: String,
        sort: Boolean,
        change:Boolean
    ): Result<Flow<PagingData<SimpleArticle>>> = runCatching {
        Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                NearByShopPagingSource(firestore,getLastLocationUseCase,category,sort,change)
            }
        ).flow
    }

}