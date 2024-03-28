package com.dkproject.presentation.navigation.writeshop

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkproject.presentation.ui.screen.home.shop.SetAddressScreen
import com.dkproject.presentation.ui.screen.home.shop.WriteShopScreen
import com.dkproject.presentation.ui.screen.home.shop.WriteShopViewModel
import kotlin.math.log

enum class WriteShopRoute(val route: String) {
    WRITE("WriteShopScreen"),
    ADDRESS("SetAddressScreen")
}

@Composable
fun WriteShopNavigation(navController: NavHostController = rememberNavController(),
                        onBackClick:()->Unit,
                        onLoad:()->Unit) {
    val sharedViewModel:WriteShopViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = WriteShopRoute.WRITE.route
    ) {
        composable(route=WriteShopRoute.WRITE.route){
            WriteShopScreen(viewModel = sharedViewModel,
                setAddress = {
                             navController.navigate(route = WriteShopRoute.ADDRESS.route)
                },
                onBackClick = onBackClick,
                onLoad =onLoad)
        }
        composable(route=WriteShopRoute.ADDRESS.route){
            SetAddressScreen(sharedViewModel = sharedViewModel, finish = {
                Log.d("finish","fiinish")
                navController.navigate(WriteShopRoute.WRITE.route)
            })
        }
    }
}