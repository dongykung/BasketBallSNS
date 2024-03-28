package com.dkproject.presentation.ui.screen.home.shop

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WriteShopViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
):ViewModel() {
    private val _state = MutableStateFlow<WriteState>(WriteState("", emptyList(),
        "","","","",0.0,0.0))
    val state : StateFlow<WriteState> = _state.asStateFlow()

    fun updateName(name:String){
        _state.update {
            it.copy(name=name)
        }
    }

    fun addImageList(uri: Uri){
        _state.update {
            it.copy(imageList = state.value.imageList+uri)
        }
    }

    fun removeImageList(uri:Uri){
        _state.update {
            it.copy(
                imageList = state.value.imageList-uri
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

    fun uploadArticle(){
        viewModelScope.launch {

        }
    }


}



data class WriteState(
    val name:String,
    val imageList:List<Uri>,
    val price:String,
    val type:String,
    val content:String,
    val detailAddress:String,
    val lat:Double,
    val lng:Double,
)