package com.dkproject.presentation.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.ui.screen.home.profile.EditProfileScreen
import com.dkproject.presentation.ui.screen.home.profile.EditProfileViewModel
import com.dkproject.presentation.ui.screen.home.profile.UserInfoUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            intent?.run {
                if (hasExtra("UserInfo")) {
                    val userParcel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        getParcelableExtra("UserInfo", UserInfoUiModel::class.java)
                    } else {
                        getParcelableExtra("UserInfo")
                    }
                    Log.d("EditProfileActivity", userParcel.toString())
                   userParcel?.run {
                       val viewModel : EditProfileViewModel = viewModel()
                       viewModel.updateUiState(this)
                       EditProfileScreen(viewModel = viewModel,
                           onBackClick = {finish()},
                           onChangeClick = {
                               setResult(Activity.RESULT_OK, Intent())
                               finish()
                           })

                   }
                }
            }
        }
    }
}