package com.dkproject.presentation.ui.screen.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase
):ViewModel() {

    fun logOut(moveToLogin:()->Unit){
        viewModelScope.launch {
            clearTokenUseCase()
            moveToLogin()
        }

    }
}