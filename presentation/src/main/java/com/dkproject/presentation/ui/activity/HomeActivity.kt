package com.dkproject.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkproject.presentation.navigation.home.HomeNavigation
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity :ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BasketballSNSTheme {
                HomeNavigation()
            }
        }
    }
}