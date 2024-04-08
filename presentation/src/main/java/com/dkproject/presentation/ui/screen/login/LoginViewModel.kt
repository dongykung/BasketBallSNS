package com.dkproject.presentation.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.usecase.login.CheckFirstUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.token.SetTokenUseCase
import com.dkproject.presentation.ui.component.showToastMessage
import com.dkproject.presentation.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
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
    val autoLogin: State<Boolean> = _autoLogin

    val googleClientIntent = googleClient.signInIntent

    init {
        autoLogin()
    }

    fun autoLogin() {
        viewModelScope.launch {
            if (!getTokenUseCase().isNullOrEmpty()){
                Constants.myToken = getTokenUseCase()!!
                _autoLogin.value = true
            }
        }
    }

    fun googleSignIn(
        credential: AuthCredential,
        moveToHome: () -> Unit,
        moveToFirst: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = firebaseAuth.signInWithCredential(credential).await().user
            if (result?.uid != null) {
                //만약 처음 구글 로그인을 했다면 유저정보 설정 페이지로 이동
                if (checkFirstUseCase(result.uid)) {
                    moveToFirst(result.uid)
                }
                //이미 구글로그인한 기록이 있다면 홈 화면으로 이동
                else {
                    setTokenUseCase(result.uid)
                    Constants.myToken = result.uid
                    moveToHome()
                }
            }
        }
    }

    fun kakaoSignIn(
        context: Context,
        moveToHome: () -> Unit,
        moveToFirst: (String) -> Unit
    ) {
        viewModelScope.launch {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                    showToastMessage(context, "카카오계정으로 로그인 실패")
                } else if (token != null) {
                    checkKakao(
                        moveToHome=moveToHome,
                        moveToFirst=moveToFirst
                    )
                }
            }

            UserApiClient.instance.me { user, error ->
                Log.e(TAG, user.toString())
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)
                        showToastMessage(context, "카카오계정으로 로그인 실패")
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        checkKakao(
                            moveToHome=moveToHome,
                            moveToFirst=moveToFirst
                        )
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    fun checkKakao(
        moveToHome: () -> Unit,
        moveToFirst: (String) -> Unit
    ){
        UserApiClient.instance.me { user, error ->
            viewModelScope.launch {
                if(user!=null){
                    if (checkFirstUseCase(user.id.toString())) {
                        moveToFirst(user.id.toString())
                    }
                    //이미 구글로그인한 기록이 있다면 홈 화면으로 이동
                    else {
                        setTokenUseCase(user.id.toString())
                        Constants.myToken = user.id.toString()
                        moveToHome()
                    }
                }
            }
        }
    }
}