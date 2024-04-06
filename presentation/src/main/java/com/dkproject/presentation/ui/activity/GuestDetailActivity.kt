package com.dkproject.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.presentation.navigation.home.UserFirstNavigation
import com.dkproject.presentation.ui.screen.home.home.guest.GuestScreen
import com.dkproject.presentation.ui.screen.home.home.guest.GuestViewModel
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestDetailActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BasketballSNSTheme {
                val value = intent.getStringExtra("uid")
                if (value != null) {
                    val viewModel:GuestViewModel = hiltViewModel()
                    viewModel.getGuestItem(value)
                    GuestScreen(uid=value,viewModel = viewModel,
                        onBackClick = {finish()})
                }

            }
        }
    }
}