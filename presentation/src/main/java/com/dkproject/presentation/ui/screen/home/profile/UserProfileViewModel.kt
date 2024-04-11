package com.dkproject.presentation.ui.screen.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
):ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState(
        UserInfo(
        "", "", "", emptyList(), emptyList(), "")
    ))
    val state : StateFlow<ProfileUiState> = _state.asStateFlow()

    fun getUserInfo(uid:String){
        viewModelScope.launch {
            getUserInfoUseCase(uid).collect{userInfo->
                when(userInfo){
                    is Resource.Loading->{

                    }
                    is Resource.Error->{

                    }
                    is Resource.Success->{
                        updateUserInfo(userInfo.data!!)
                    }
                }
            }
        }
    }

    fun updateUserInfo(userInfo: UserInfo){
        _state.update { it.copy(userInfo = userInfo) }
    }
}



