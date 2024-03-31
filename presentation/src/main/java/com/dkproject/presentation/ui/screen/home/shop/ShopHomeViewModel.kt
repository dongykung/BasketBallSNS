package com.dkproject.presentation.ui.screen.home.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.log
import androidx.paging.map
import com.dkproject.domain.model.shop.SimpleArticle
import com.dkproject.domain.usecase.shop.GetArticleUseCase
import com.dkproject.domain.usecase.shop.GetNearByArticleUseCase
import com.dkproject.presentation.model.ShopUiModel
import com.dkproject.presentation.model.toUiModel
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShopHomeViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val getNearByArticleUseCase: GetNearByArticleUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ShopHomeState("모두보기", true, false, emptyFlow()))
    val state: StateFlow<ShopHomeState> = _state.asStateFlow()


    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            var shopModelFlow: Flow<PagingData<ShopUiModel>>
            getArticleUseCase(
                state.value.type,
                state.value.priceOrder,
                state.value.distanceOrder
            ).onSuccess {
                shopModelFlow = it.map { pagingData ->
                    Log.d("getArticle", pagingData.toString())
                    pagingData.map { article ->
                        article.toUiModel()
                    }
                }

                _state.update { it.copy(shopList = shopModelFlow) }
            }.onFailure {
                Log.d("getArticleFail", it.toString())
            }
        }
    }

    fun nearyDataload() {
        viewModelScope.launch {
            var shopModelFlow: Flow<PagingData<ShopUiModel>>
            getNearByArticleUseCase(
                state.value.type,
                state.value.priceOrder,
                state.value.distanceOrder
            ).onSuccess {
                shopModelFlow = it.map { pagingData ->
                    pagingData.map { article ->
                        article.toUiModel()
                    }
                }
                _state.update { it.copy(shopList = shopModelFlow) }
            }.onFailure {

            }
        }
    }

    fun categoryChange(category: String) {
        _state.update { it.copy(type = category) }
        if (state.value.distanceOrder) {
            Log.d("near", "nearcategory")
            nearyDataload()
        } else
            load()
    }

    fun updatePrice(price: Boolean) {
        _state.update { it.copy(priceOrder = price) }
        if (state.value.distanceOrder) {
            Log.d("near", "nearprice")
            nearyDataload()
        } else
            load()

    }

    fun updateDistance(distance: Boolean) {
        _state.update { it.copy(distanceOrder = distance) }
        if (state.value.distanceOrder) {
            Log.d("near", "neardis")
            nearyDataload()
        } else
            load()
    }

}


data class ShopHomeState(
    val type: String,
    val priceOrder: Boolean,
    val distanceOrder: Boolean,
    val shopList: Flow<PagingData<ShopUiModel>>
)