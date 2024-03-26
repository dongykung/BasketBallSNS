package com.dkproject.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkproject.presentation.navigation.home.UserFirstNavigation
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasketballSNSTheme {
                val value = intent.getStringExtra("Uid")
                if (value != null)
                    UserFirstNavigation(createUid = value, onCancel = {
                        finish()
                    })
            }
        }
    }
}