package com.dkproject.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkproject.presentation.ui.screen.login.LoginScreen
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasketballSNSTheme {
                LoginScreen()
            }
        }
    }
}