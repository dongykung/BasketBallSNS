package com.dkproject.presentation.ui.screen.home.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.usecase.home.UploadGuestUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.presentation.util.converTimeMills
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
    private val uploadGuestUseCase: UploadGuestUseCase
):ViewModel() {

    companion object{
        const val TAG = "WRTIEJOBVIEWMODEL"
    }
    private val _state = MutableStateFlow(jobState("",uid=UUID.randomUUID().toString(),"", emptyList(),0,
        "",0.0,0.0,"",time="00:00",hour=0,min=0))
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
    fun uploadGuest(
        success:()->Unit,
        loading:()->Unit,
        failed:()->Unit
    ){
        viewModelScope.launch {
            val writeuid=getTokenUseCase()
            _state.update { it.copy(writeUid=writeuid!!) }
            _state.update { it.copy(detaildate = converTimeMills(state.value.detaildate,state.value.hour,state.value.min)) }
            uploadGuestUseCase(state.value.toDomainModel()).onEach {result->
                when(result){
                    is Resource.Success->{
                        Log.d(TAG, "upload Success")
                        success()
                    }
                    is Resource.Loading->{
                        Log.d(TAG, "loading..")
                        loading()
                    }
                    is Resource.Error->{
                        failed()
                        Log.d(TAG, result.message?:"Upload Failed")
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
            it.copy(detaildate=date)
        }
    }
    fun updateTime(time:String){
        _state.update {
            it.copy(time=time)
        }
    }
    fun updateHour(hour:Int){
        _state.update { it.copy(hour=hour) }
    }

    fun updatedaydate(date:Long){
        _state.update { it.copy(daydate = date) }
    }
    fun updateMin(min:Int){
        _state.update { it.copy(min=min) }
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
    val detaildate:Long=System.currentTimeMillis(),
    val daydate:Long= System.currentTimeMillis(),
    val time:String,
    val hour:Int,
    val min:Int,
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
            guestsUid = emptyList(),
            detailAddress=detailAddress,
            detaildate=detaildate,
            daydate=daydate
        )
    }
}