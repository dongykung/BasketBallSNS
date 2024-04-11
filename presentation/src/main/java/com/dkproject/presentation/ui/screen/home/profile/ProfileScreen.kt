package com.dkproject.presentation.ui.screen.home.profile

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.presentation.ui.activity.LoginActivity
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.profile.UserInfoSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = state.userInfo.nickname)})
    }) {innerPadding->
        Column(modifier = Modifier.padding(innerPadding)) {
            UserInfoSection(modifier=Modifier.fillMaxSize(),state.userInfo)
        }
    }
}