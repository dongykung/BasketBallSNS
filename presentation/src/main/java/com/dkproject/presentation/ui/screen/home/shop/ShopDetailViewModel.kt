package com.dkproject.presentation.ui.screen.home.shop

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.usecase.shop.DeleteArticleUseCase
import com.dkproject.domain.usecase.shop.GetArticleItemUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.presentation.ui.screen.home.home.guest.GuestViewModel
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShopDetailViewModel @Inject constructor(
    private val getArticleItemUseCase: GetArticleItemUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) : ViewModel() {
    companion object{
        const val TAG="ShopDetailViewModel"
    }

    private val _state = MutableStateFlow(
        ShopDetailUiState(UserInfo("", "", "", emptyList(), emptyList(),""),
            Articles("", "", "", emptyList(), 0, "", "", "", 0.0, 0.0)
        )
    )
    val state : StateFlow<ShopDetailUiState> = _state.asStateFlow()
    var loading by mutableStateOf(false)
    var error by mutableStateOf(false)
    var myshop by mutableStateOf(false)
    fun updateArticle(articles: Articles){
        _state.update { it.copy(articleInfo = articles) }
    }
    fun getArticle(uid:String){
        viewModelScope.launch {
            Log.d(TAG, "start")
            getArticleItemUseCase(uid).collect{
                when(it){
                    is Resource.Loading->{
                        Log.d(TAG, "Loading")
                        loading=true
                        error=false
                    }
                    is Resource.Error->{
                        Log.d(TAG, "Error")

                        loading=false
                        error=true
                    }
                    is Resource.Success->{
                        Log.d(TAG, it.data.toString())
                        loading=false
                        error=false
                        myshop = (it.data?.writerUid==Constants.myToken)
                        updateArticle(it.data!!)
                        getWriterInfo(it.data?.writerUid ?: "")
                    }
                }
            }
        }
    }

    fun getWriterInfo(uid:String){
        viewModelScope.launch {
            Log.d(GuestViewModel.TAG, uid)
            getUserInfoUseCase(uid).collect{result->
                when(result){
                    is Resource.Success->{
                        _state.update { it.copy(userInfo = result.data!!) }
                        Log.d(GuestViewModel.TAG, result.data.toString())
                    }
                    is Resource.Loading->{
                        Log.d(GuestViewModel.TAG, result.toString())
                    }
                    is Resource.Error->{
                        Log.d(GuestViewModel.TAG, result.toString())
                    }
                }
            }
        }
    }

    fun deleteArticle(deleteCompleted:()->Unit){
        viewModelScope.launch {
            deleteArticleUseCase(state.value.articleInfo.uid).onSuccess {
                deleteCompleted()
            }.onFailure {

            }
        }
    }
}


data class ShopDetailUiState(
    val userInfo: UserInfo,
    val articleInfo: Articles
)