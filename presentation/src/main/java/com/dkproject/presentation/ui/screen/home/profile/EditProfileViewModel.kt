package com.dkproject.presentation.ui.screen.home.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.domain.usecase.user.SetUserInfoUseCase
import com.dkproject.domain.usecase.user.UploadProfileImageUseCase
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
class EditProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val setUserInfoUseCase: SetUserInfoUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        EditProfileUiState(
            UserInfoUiModel(
                "", "",
                "", emptyList(), emptyList(), ""
            )
        )
    )
    val state: StateFlow<EditProfileUiState> = _state.asStateFlow()
    var loading by mutableStateOf(false)
    var profileChange by mutableStateOf(false)
    fun updateUiState(userInfo: UserInfoUiModel) {
        _state.update { it.copy(userInfo = userInfo) }
    }

    fun updateProfileImage(userInfo: UserInfoUiModel) {
        _state.update { it.copy(userInfo = userInfo) }
    }

    fun changeUserInfo(context: Context, moveToProfile: () -> Unit) {
        viewModelScope.launch {
            if (profileChange) {
                Log.d("EditProfileViewModel", "changeprofilephoto")
                val result = uploadProfileImageUseCase(
                    Constants.myToken,
                    state.value.userInfo.profileImageUrl
                )
                val data = state.value.userInfo.copy()
                data.profileImageUrl = result
                updateProfileImage(data)
                if (setUserInfoUseCase(state.value.userInfo.toDomainModel())) {
                    loading = true
                    moveToProfile()
                } else {
                    loading = false
                    showToastMessage(context, "프로필 수정에 실패하였습니다")
                }
            } else {
                Log.d("EditProfileViewModel", "noChangePhoto")
                if (setUserInfoUseCase(state.value.userInfo.toDomainModel())) {
                    loading = true
                    moveToProfile()
                } else {
                    loading = false
                    showToastMessage(context, "프로필 수정에 실패하였습니다")
                }
            }
        }
    }


}


data class EditProfileUiState(
    val userInfo: UserInfoUiModel
)