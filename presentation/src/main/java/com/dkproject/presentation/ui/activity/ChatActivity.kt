package com.dkproject.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.ui.screen.home.chat.ChatScreen
import com.dkproject.presentation.ui.screen.home.chat.ChatViewModel
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasketballSNSTheme {
                val chatUserUid = intent.getStringExtra("UserUid") ?: "test"
                Log.e("ChatActivity", chatUserUid)
                val chatViewModel:ChatViewModel = viewModel()
                chatViewModel.load(chatUserUid)
                chatViewModel.getChatUserInfo(chatUserUid)
                ChatScreen(modifier= Modifier.fillMaxSize(),
                    otherUid = chatUserUid,
                    viewModel=chatViewModel,
                    onBackClick = { finish() })
            }
        }
    }
}