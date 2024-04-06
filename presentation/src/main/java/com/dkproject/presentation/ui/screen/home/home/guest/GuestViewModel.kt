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
) :ViewModel(){
    companion object{
        const val TAG="GuestViewModel"
    }
    private val _state = MutableStateFlow(GuestItemState(Guest("","","", emptyList(),
        0,"",0.0,0.0, emptyList(),"",0,0
    ),UserInfo("","","", emptyList(), emptyList(),"")))
    val state :StateFlow<GuestItemState> = _state.asStateFlow()
    var loading by mutableStateOf(false)
    var error by mutableStateOf(false)


    fun getGuestItem(uid:String) {
        viewModelScope.launch {
            getGuestItemUseCase(uid).collect { result->
              when(result){
                  is Resource.Success->{
                      Log.d(TAG, "Success")
                      loading=false
                      error=false
                      if(result.data!=null) {
                          _state.update { it.copy(guest = result.data!!) }
                          getWriterInfo(result.data?.writeUid!!)
                      }else{
                          error=true
                      }
                  }
                  is Resource.Loading->{
                      Log.d(TAG, "Loading")
                      loading = true
                      error=false
                  }
                  is Resource.Error->{
                      Log.d(TAG, "Error")
                      loading=false
                      error=true
                  }
              }
            }
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

}

data class GuestItemState(
    val guest:Guest,
    val userInfo:UserInfo
)

