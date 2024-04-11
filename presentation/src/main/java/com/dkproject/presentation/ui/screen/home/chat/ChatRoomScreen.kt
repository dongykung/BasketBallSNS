package com.dkproject.presentation.ui.screen.home.chat

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.presentation.ui.activity.ChatActivity
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.chat.ChatListCard
import com.dkproject.presentation.ui.screen.home.home.GuestUiModel

@Composable
fun ChatRoomScreen(viewModel: ChatRoomViewModel= hiltViewModel()){
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    ChatRoomScreen(chatRoomList = state, chatRoomClick = {
        context.startActivity(Intent(context,ChatActivity::class.java).apply {
            putExtra("UserUid",it)
        })
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun ChatRoomScreen(
    chatRoomList:ChatRoomUiState,
    chatRoomClick:(String)->Unit={}
) {
    Log.d("ChatRoomScreen", "ChatRoomScreen: ")

    Scaffold(topBar = {
        HomeTopAppBar(title = "채팅")

    }) {innerPadding->

        Column(modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
           ChatRoomList(roomList = chatRoomList, clickchat = chatRoomClick)
        }
    }
}

@Composable
fun ChatRoomList(
    roomList : ChatRoomUiState,
    clickchat:(String) ->Unit
){
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp)) {
        items(  count = roomList.chatRoomList.size,
            key = { index ->
                roomList.chatRoomList[index].chatRoomInfo.chatRoomId ?: index
            }){index->
            roomList.chatRoomList[index].run {
                ChatListCard(modifier=Modifier.clickable { clickchat(this.userInfo.useruid) },
                    chatRoom = this.chatRoomInfo, userInfo = this.userInfo)
            }
        }
    }
}