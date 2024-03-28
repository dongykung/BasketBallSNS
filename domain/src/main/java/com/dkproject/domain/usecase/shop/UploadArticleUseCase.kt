package com.dkproject.domain.usecase.shop

import com.dkproject.domain.model.shop.Articles

interface UploadArticleUseCase {
    operator suspend fun invoke(article:Articles):Boolean
}