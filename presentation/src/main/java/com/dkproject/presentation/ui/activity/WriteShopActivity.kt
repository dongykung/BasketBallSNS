package com.dkproject.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkproject.presentation.navigation.writeshop.WriteShopNavigation
import com.dkproject.presentation.ui.screen.home.shop.WriteShopScreen
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WriteShopActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BasketballSNSTheme {
                WriteShopNavigation(onBackClick = {finish()})
            }
        }
    }
}