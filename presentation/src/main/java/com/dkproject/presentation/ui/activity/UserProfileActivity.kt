package com.dkproject.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.ui.screen.home.profile.UserProfileScreen
import com.dkproject.presentation.ui.screen.home.profile.UserProfileViewModel
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserProfileActivity :ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BasketballSNSTheme {
                val value = intent.getStringExtra("userUid")
                if(value!=null){
                    val viewModel:UserProfileViewModel = hiltViewModel()
                    viewModel.getUserInfo(value)
                    UserProfileScreen(viewModel = viewModel){
                        finish()
                    }
                }
            }
        }
    }
}