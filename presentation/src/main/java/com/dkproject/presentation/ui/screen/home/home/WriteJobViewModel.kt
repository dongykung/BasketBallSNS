package com.dkproject.presentation.ui.screen.home.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.usecase.home.UploadGuestTestUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WriteJobViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val uploadGuestTestUseCase: UploadGuestTestUseCase
):ViewModel() {

    private val _state = MutableStateFlow(jobState("",uid=UUID.randomUUID().toString(),"", emptyList(),0,
        "",0.0,0.0,"",time="00:00"))
    val state : StateFlow<jobState> = _state

    fun updatePositionList(position:String){
        if(state.value.positionList.contains(position)){
            val list = state.value.positionList-position
            _state.update {
                it.copy(positionList = list)
            }
        }else{
            val list = state.value.positionList+position
            _state.update {
                it.copy(positionList = list)
            }
        }
    }
    fun uploadGuest(){
        viewModelScope.launch {
            val writeuid=getTokenUseCase()
            _state.update { it.copy(writeUid=writeuid!!) }
            uploadGuestTestUseCase(state.value.toDomainModel()).onEach {result->
                when(result){
                    is Resource.Success->{
                        Log.d("good", "uploadGuest: ")
                    }
                    is Resource.Loading->{
                        Log.d("good", "loading: ")
                    }
                    is Resource.Error->{
                        Log.d("good", "error: ")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateCount(count:Int){
        _state.update {
            it.copy(count=count)
        }
    }
    fun updateContent(content:String){
        _state.update {
            it.copy(content=content)
        }
    }
    fun updateTitle(title:String){
        _state.update {
            it.copy(title=title)
        }
    }
    fun updateLat(lat:Double){
        _state.update {
            it.copy(lat=lat)
        }
    }
    fun updateLng(lng:Double){
        _state.update {
            it.copy(lng=lng)
        }
    }

    fun updateDetailAddress(address:String){
        _state.update {
            it.copy(detailAddress = address)
        }
    }
    fun updateDate(date:Long){
        _state.update {
            it.copy(date=date)
        }
    }
    fun updateHour(time:String){
        _state.update {
            it.copy(time=time)
        }
    }

}


data class jobState(
    val writeUid:String,
    val uid:String,
    val title:String,
    val positionList:List<String>,
    val count:Int,
    val content:String,
    val lat:Double,
    val lng:Double,
    val detailAddress:String,
    val date:Long=System.currentTimeMillis(),
    val time:String
){
    fun toDomainModel() : Guest{
        return Guest(
            writeUid=writeUid,
            uid=uid,
            title=title,
            positionList=positionList,
            count=count,
            content=content,
            lat=lat,
            lng=lng,
            detailAddress=detailAddress,
            date=date,
            time=time
        )
    }
}