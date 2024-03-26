package com.dkproject.presentation.ui.screen.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.usecase.login.CheckFirstUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.token.SetTokenUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleClient: GoogleSignInClient,
    private val checkFirstUseCase: CheckFirstUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }
   private val _autoLogin = mutableStateOf(false)
    val autoLogin :State<Boolean> = _autoLogin

    val googleClientIntent = googleClient.signInIntent

    init {
        autoLogin()
    }
    fun autoLogin(){
        viewModelScope.launch {
            if(!getTokenUseCase().isNullOrEmpty())
                _autoLogin.value = true
        }
    }

    fun googleSignIn(
        credential: AuthCredential,
        moveToHome: () -> Unit,
        moveToFirst: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = firebaseAuth.signInWithCredential(credential).await().user
            if (result?.uid != null){
                //만약 처음 구글 로그인을 했다면 유저정보 설정 페이지로 이동
                if(checkFirstUseCase(result.uid)){
                    moveToFirst(result.uid)
                }
                //이미 구글로그인한 기록이 있다면 홈 화면으로 이동
                else{
                    setTokenUseCase(result.uid)
                    moveToHome()
                }
            }
        }
    }
}