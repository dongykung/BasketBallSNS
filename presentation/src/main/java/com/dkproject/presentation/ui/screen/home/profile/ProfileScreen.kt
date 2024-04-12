package com.dkproject.presentation.ui.screen.home.profile

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.presentation.ui.activity.EditProfileActivity
import com.dkproject.presentation.ui.activity.LoginActivity
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.profile.UserInfoSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    moveToEditProfile:(UserInfoUiModel)->Unit
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = state.userInfo.nickname) },
                actions = {
                    IconButton(onClick = {
                        viewModel.logOut(){
                            context.startActivity(Intent(context,LoginActivity::class.java).apply {
                                flags =Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                        }
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.Logout, contentDescription = "Logout")
                    }
                })
        },
    ) {innerPadding->
        Column(modifier = Modifier.padding(innerPadding)) {
            UserInfoSection(modifier=Modifier.fillMaxSize(),state.userInfo){
                val userData = UserInfoUiModel(
                    useruid = state.userInfo.useruid,
                    nickname = state.userInfo.nickname,
                    profileImageUrl = state.userInfo.profileImageUrl,
                    playPosition = state.userInfo.playPosition,
                    playStyle = state.userInfo.playStyle,
                    userSkill = state.userInfo.userSkill
                )
                moveToEditProfile(userData)
            }
            Spacer(modifier = Modifier.height(15.dp))

        }
    }
}