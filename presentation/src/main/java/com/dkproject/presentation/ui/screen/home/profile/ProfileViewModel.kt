package com.dkproject.presentation.ui.screen.home.profile

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState(UserInfo(
                "", "", "", emptyList(), emptyList(), "")))
    val state : StateFlow<ProfileUiState> = _state.asStateFlow()


    init {
        load()
    }

    fun load(){
        viewModelScope.launch {
            getUserInfoUseCase(Constants.myToken).collect{userInfo->
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


    fun logOut(moveToLogin: () -> Unit) {
        viewModelScope.launch {
            clearTokenUseCase()
            moveToLogin()
        }
    }
}


data class ProfileUiState(
    val userInfo: UserInfo
)

@Parcelize
data class UserInfoUiModel(
    val useruid:String,  //
    val nickname: String, //
    var profileImageUrl: String, //
    val playPosition:List<String>, //
    val playStyle:List<String>, //
    val userSkill:String, //
):Parcelable {
    fun toDomainModel():UserInfo{
        return UserInfo(
            useruid=useruid,
            nickname=nickname,
            profileImageUrl=profileImageUrl,
            playPosition=playPosition,
            playStyle = playStyle,
            userSkill=userSkill
        )
    }
}