package com.dkproject.domain.usecase.shop

import com.dkproject.domain.repository.ShopRepository

class DeleteArticleUseCase(private val shopRepository: ShopRepository) {
    suspend operator fun invoke(uid:String):Result<Boolean> = runCatching {
        shopRepository.deleteArticle(uid)
    }
}