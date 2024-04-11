package com.dkproject.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.ui.screen.home.home.guest.GuestScreen
import com.dkproject.presentation.ui.screen.home.home.guest.GuestViewModel
import com.dkproject.presentation.ui.screen.home.shop.ShopDetailScreen
import com.dkproject.presentation.ui.screen.home.shop.ShopDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopDetailActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val value = intent.getStringExtra("uid")
            if (value != null) {
                Log.d("uid", value)
                val viewModel: ShopDetailViewModel = viewModel()
                viewModel.getArticle(value)
                ShopDetailScreen(viewModel = viewModel, shopUid = value) {
                    finish()
                }
            }
        }
    }
}