package com.dkproject.presentation.ui.screen.home.shop

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class WriteShopViewModel @Inject constructor():ViewModel() {
    private val _state = MutableStateFlow<WriteState>(WriteState("", emptyList(),0,"",""))
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

    fun updatePrice(price:Long){
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
}



data class WriteState(
    val name:String,
    val imageList:List<Uri>,
    val price:Long,
    val type:String,
    val content:String
)