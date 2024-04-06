package com.dkproject.presentation.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkproject.presentation.navigation.writejob.WriteJobNavigation
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WriteJobActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasketballSNSTheme {
                WriteJobNavigation(onBackClick = {
                    finish()
                },
                    onLoad = {
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    })
            }
        }
    }
}