package com.dkproject.presentation.ui.screen.home.home.guest

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.usecase.home.ApplyCancelUseCase
import com.dkproject.domain.usecase.home.ApplyGuestUseCase
import com.dkproject.domain.usecase.home.GetGuestItemUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.presentation.ui.component.showToastMessage
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GuestViewModel @Inject constructor(
    private val getGuestItemUseCase: GetGuestItemUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val applyGuestUseCase: ApplyGuestUseCase,
    private val applyCancelUseCase: ApplyCancelUseCase
) :ViewModel(){
    companion object{
        const val TAG="GuestViewModel"
    }
    private val _state = MutableStateFlow(GuestItemState(Guest("","","", emptyList(),
        0,"",37.3860147,126.9774774, emptyList(),"",0,0
    ),UserInfo("","","", emptyList(), emptyList(),""), emptyList()
    ))
    val state :StateFlow<GuestItemState> = _state.asStateFlow()
    var loading by mutableStateOf(false)
    var error by mutableStateOf(false)
    var getuser by mutableStateOf(false)
    var applystatus by mutableStateOf(state.value.guest.guestsUid.contains(Constants.myToken))

    fun getGuestItem(uid:String) {
        viewModelScope.launch {
            getGuestItemUseCase(uid).collect { guest ->
                Log.d(TAG, guest.toString())
                _state.update { it.copy(guest = guest) }
                if (!getuser) {
                    getWriterInfo(guest.writeUid)
                    getuser=true
                }

                    updateApplyGuestsInfo(guest.guestsUid)

            }
        }
    }

    fun updateApplyGuestsInfo(list : List<String>){
        viewModelScope.launch {
            val guestsList : MutableList<UserInfo> = mutableListOf()
            list.forEach{
                getUserInfoUseCase(it).collect{userInfo->
                    when(userInfo){
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {guestsList.add(userInfo.data!!)}
                    }
                }
            }
            Log.d(TAG, "updateApplyGuestsInfo: ")
            _state.update { it.copy(guestsInfo = guestsList) }
        }
    }

    fun getWriterInfo(uid:String){
        viewModelScope.launch {
            Log.d(TAG, uid)
            getUserInfoUseCase(uid).collect{result->
                when(result){
                    is Resource.Success->{
                        _state.update { it.copy(userInfo = result.data!!) }
                        Log.d(TAG, result.data.toString())
                    }
                    is Resource.Loading->{
                        Log.d(TAG, result.toString())
                    }
                    is Resource.Error->{
                        Log.d(TAG, result.toString())
                    }
                }
            }
        }
    }

    fun applyGuest(context: Context){
        viewModelScope.launch {
            applyGuestUseCase(state.value.guest.uid,state.value.guest.guestsUid+Constants.myToken).collect{result->
                when(result){
                    is Resource.Error -> {
                        showToastMessage(context,result.message.toString())
                    }
                    is Resource.Loading ->{}
                    is Resource.Success -> {
                        showToastMessage(context,"신청이 완료되었습니다")
                        applystatus=true
                    }
                }
            }
        }
    }

    fun applyCancel(context: Context){
        viewModelScope.launch {
            Log.d(TAG, "applyCancel: ")
            val list = if(state.value.guest.guestsUid.contains(Constants.myToken))
                (state.value.guest.guestsUid - Constants.myToken) else state.value.guest.guestsUid
            applyCancelUseCase(state.value.guest.uid,list).collect{
                applystatus=false
                showToastMessage(context,"신청이 취소되었습니다")
            }
        }
    }

}

data class GuestItemState(
    val guest:Guest,
    val userInfo:UserInfo,
    val guestsInfo:List<UserInfo>
)

