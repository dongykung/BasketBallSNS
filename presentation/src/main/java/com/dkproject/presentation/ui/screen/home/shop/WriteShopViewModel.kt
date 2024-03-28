package com.dkproject.presentation.ui.screen.home.shop

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.shop.Articles
import com.dkproject.domain.usecase.file.UploadImageListUseCase
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.dkproject.domain.usecase.shop.UploadArticleUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.presentation.ui.component.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class WriteShopViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val uploadImageListUseCase: UploadImageListUseCase,
    private val uploadArticleUseCase: UploadArticleUseCase
):ViewModel() {
    private val _state = MutableStateFlow<WriteState>(WriteState("","","", emptyList(),
        "","","","",0.0,0.0))
    val state : StateFlow<WriteState> = _state.asStateFlow()

    var loading = mutableStateOf(false)
    fun updateName(name:String){
        _state.update {
            it.copy(name=name)
        }
    }

    fun addImageList(uri: Uri){
        _state.update {
            it.copy(imageList = state.value.imageList+uri.toString())
        }
    }

    fun removeImageList(uri:Uri){
        _state.update {
            it.copy(
                imageList = state.value.imageList-uri.toString()
            )
        }
    }

    fun updatePrice(price:String){
        _state.update {
            it.copy(price=price)
        }
    }

    fun updateType(type:String){
        _state.update {
            it.copy(type=type)
        }
    }

    fun updateContent(content:String){
        _state.update {
            it.copy(content=content)
        }
    }

    fun updateDetailAddress(detailAddress:String){
        _state.update {
            it.copy(detailAddress=detailAddress)
        }
    }

    fun updatelatitude(latitude: Double){
        _state.update {
            it.copy(lat=latitude)
        }
    }

    fun updatelongitude(longitude: Double){
        _state.update {
            it.copy(lng=longitude)
        }
    }

    fun uploadArticle(context:Context,onLoad:()->Unit){
        viewModelScope.launch {
            loading.value=true
            val shopUid = UUID.randomUUID().toString()
            _state.update { it.copy(uid = shopUid) }
            val token = getTokenUseCase()
            val imageList = state.value.imageList.mapNotNull {
                uploadImageListUseCase(token!!,shopUid,it.toString())
            }
            _state.update { it.copy(imageList = imageList, writerUid = token!!) }
            if(uploadArticleUseCase(state.value.toDomainModel()))
                onLoad()
            else{
                loading.value=false
                showToastMessage(context,"상품 올리기에 실패하였습니다")
            }
        }
    }
}



data class WriteState(
    val writerUid:String,
    val uid:String,
    val name:String,
    val imageList:List<String>,
    val price:String,
    val type:String,
    val content:String,
    val detailAddress:String,
    val lat:Double,
    val lng:Double,
){
    fun toDomainModel():Articles{
        return Articles(
            writerUid=writerUid,
            uid=uid,
            name=name,
            imageList=imageList,
            price=price,
            type=type,
            content=content,
            detailAddress=detailAddress,
            lat=lat,
            lng=lng
        )
    }
}