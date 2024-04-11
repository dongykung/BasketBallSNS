package com.dkproject.presentation.ui.screen.home.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.ui.activity.ChatActivity
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.profile.userInfoDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    onBackClick: () -> Unit,
) {
    val context= LocalContext.current
    val state = viewModel.state.collectAsState().value
    Scaffold(topBar = {
        HomeTopAppBar(
            title = "",
            onBack = true,
            onBackClick = onBackClick
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = state.userInfo.profileImageUrl),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = state.userInfo.nickname,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
                onClick = {
                    context.startActivity(Intent(context,ChatActivity::class.java).apply {
                        putExtra("UserUid",state.userInfo.useruid)
                    })
                }) {
                Text(text = "채팅하기")
            }
            userInfoDetail("포지션",state.userInfo.playPosition)
            Spacer(modifier = Modifier.height(20.dp))
            userInfoDetail("스타일",state.userInfo.playStyle)
            Spacer(modifier = Modifier.height(20.dp))
            userInfoDetail("숙련도",skill=state.userInfo.userSkill)
        }
    }
}