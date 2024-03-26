package com.dkproject.presentation.navigation.home

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.dkproject.presentation.ui.activity.HomeActivity
import com.dkproject.presentation.ui.component.SettingCancelDialog
import com.dkproject.presentation.ui.component.SettingTopAppBarComponent
import com.dkproject.presentation.ui.screen.userfirst.NicknameScreen
import com.dkproject.presentation.ui.screen.userfirst.PlayPositionScreen
import com.dkproject.presentation.ui.screen.userfirst.ProfileImageScreen
import com.dkproject.presentation.ui.screen.userfirst.UserFirstViewModel
import com.dkproject.presentation.ui.screen.userfirst.UserSkillScreen


enum class UserFirstRoute(val route: String, val progress: Float) {
    NICK_NAME_SCREEN("NicknameScreen", 0.33f),
    PROFILE_IMAGE_SCREEN("ProfileImageScreen", 0.66f),
    PLAY_POSITION_SCREEN("PlayPositionScreen", 1.0f),
}

@Composable
fun UserFirstNavigation(
    createUid: String,
    navController: NavHostController = rememberNavController(),
    onCancel: () -> Unit,
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    SettingCancelDialog(
        visible = visible,
        onCancelClick = onCancel,
        onDismissRequest = { visible = false })

    Scaffold(topBar = {
        SettingTopAppBarComponent {
            visible = true
        }
    }) { innerPadding ->
        var visible by rememberSaveable {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute: UserFirstRoute =
            navBackStackEntry?.destination?.route.let { currentRoute ->
                UserFirstRoute.values().find { route -> currentRoute == route.route }
            } ?: UserFirstRoute.NICK_NAME_SCREEN

        val sharedViewModel: UserFirstViewModel = viewModel()
        sharedViewModel.updateUserUid(createUid)
        Box(modifier = Modifier.fillMaxSize()) {
            if(visible)
                CircularProgressIndicator(modifier=Modifier.align(Alignment.Center))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LinearProgressIndicator(
                    progress = currentRoute.progress,
                    modifier = Modifier.fillMaxWidth()
                )
                NavHost(
                    modifier = Modifier,
                    navController = navController,
                    startDestination = UserFirstRoute.NICK_NAME_SCREEN.route
                ) {
                    composable(route = UserFirstRoute.NICK_NAME_SCREEN.route) {
                        NicknameScreen(modifier = Modifier,
                            viewModel = sharedViewModel,
                            onNextClick = {
                                navController.navigate(UserFirstRoute.PROFILE_IMAGE_SCREEN.route)
                            })
                    }
                    composable(route = UserFirstRoute.PROFILE_IMAGE_SCREEN.route) {
                        ProfileImageScreen(modifier = Modifier,
                            viewModel = sharedViewModel,
                            onNextClick = {
                                navController.navigate(UserFirstRoute.PLAY_POSITION_SCREEN.route)
                            },
                            onPrevClick = {
                                navController.popBackStack()
                            })
                    }
                    composable(route = UserFirstRoute.PLAY_POSITION_SCREEN.route) {
                        PlayPositionScreen(viewModel = sharedViewModel,
                            onPrevClick = {
                                navController.popBackStack()
                            }, onNextClick = {
                                //userData Upload
                                visible=true
                                sharedViewModel.UserInfoUpload(context,
                                    moveToHome = {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                HomeActivity::class.java
                                            ).apply {
                                                flags =
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                            })
                                    },
                                    failupload = {visible=false})
                            })
                    }

                }
            }
        }
    }
}

