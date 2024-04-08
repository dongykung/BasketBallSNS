package com.dkproject.presentation.ui.screen.userfirst

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.usecase.token.SetTokenUseCase
import com.dkproject.domain.usecase.user.CheckNicknameUseCase
import com.dkproject.domain.usecase.user.SetUserInfoUseCase
import com.dkproject.domain.usecase.user.UploadProfileImageUseCase
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.showToastMessage
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserFirstViewModel @Inject constructor(
    private val checkNicknameUseCase: CheckNicknameUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val setUserInfoUseCase: SetUserInfoUseCase,
    private val setTokenUseCase: SetTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserFirstState("","", "",
        emptyList(), emptyList(),""
    ))
    val state: StateFlow<UserFirstState> = _state.asStateFlow()

    companion object {
        const val TAG = "USERFIRST_VIEWMODEL"
    }

    fun UserInfoUpload(
        context: Context,
        moveToHome:()->Unit,
        failupload:()->Unit
    ){
        viewModelScope.launch {
            val result = uploadProfileImageUseCase(state.value.useruid,state.value.profileImageUrl)
            if(result!="FAIL"){
                updateProfileImage(result)
                if(setUserInfoUseCase(state.value.toDomainModel())){
                    setTokenUseCase(state.value.useruid)
                    Constants.myToken = state.value.useruid
                    moveToHome()
                }else{
                    showToastMessage(context,context.getString(R.string.failedsetting))
                    failupload()
                }
            }else{
                showToastMessage(context,context.getString(R.string.failedsetting))
                failupload()
            }
        }
    }

    fun updateUserUid(uid:String){
        _state.update {
            it.copy(useruid = uid)
        }
    }

    fun checkNickname(nickname:String,
                      moveToNext:()->Unit,
                      duplicatedNick:()->Unit){
        viewModelScope.launch {
            if(checkNicknameUseCase(nickname)){
                //사용 가능하기 때문에 다음 스탭
                moveToNext()
            }else{
                //이미 존재하기 때문에 다이얼로그를 띄운다
                duplicatedNick()
            }
        }
    }

    fun updateNickname(nickName: String) {
        _state.update {
            it.copy(nickname = nickName)
        }
        Log.d(TAG, state.value.nickname.length.toString())
    }

    fun updateProfileImage(uri:String){
        _state.update {
            it.copy(profileImageUrl =uri)
        }
    }

    fun updateUserPlayPosition(position:String){
        if(state.value.playPosition.contains(position)){
            val list = state.value.playPosition-position
            _state.update {
                it.copy(playPosition = list)
            }
        }else{
            val list = state.value.playPosition+position
            _state.update {
                it.copy(playPosition = list)
            }
        }
    }

    fun updateUserPlayStyle(style:String){
        if(state.value.playStyle.contains(style)){
            val list = state.value.playStyle-style
            _state.update {
                it.copy(playStyle = list)
            }
        }else{
            val list = state.value.playStyle+style
            _state.update {
                it.copy(playStyle = list)
            }
        }
    }

    fun updateUserSkill(skill:String){
        if (!state.value.equals(skill)){
            _state.update {
                it.copy(userSkill = skill)
            }
        }
    }

}


data class UserFirstState(
    val useruid:String,
    val nickname: String,
    val profileImageUrl: String,
    val playPosition:List<String>,
    val playStyle:List<String>,
    val userSkill:String,
){
    fun toDomainModel():UserInfo{
        return UserInfo(
            useruid=this.useruid,
            nickname=this.nickname,
            profileImageUrl=this.profileImageUrl,
            playPosition=this.playPosition,
            playStyle=this.playStyle,
            userSkill=this.userSkill
        )
    }
}