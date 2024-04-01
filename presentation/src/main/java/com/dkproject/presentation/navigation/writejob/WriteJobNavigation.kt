package com.dkproject.presentation.navigation.writejob

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkproject.presentation.navigation.writeshop.WriteShopRoute
import com.dkproject.presentation.ui.screen.home.home.WriteJobScreen
import com.dkproject.presentation.ui.screen.home.home.WriteJobViewModel
import com.dkproject.presentation.ui.screen.home.shop.SetAddressScreen

enum class WriteJobRoute(val route: String) {
    WRITE("WriteJobScreen"),
    ADDRESS("SetAddressScreen")
}

@Composable
fun WriteJobNavigation(
    navController: NavHostController = rememberNavController(),
    onBackClick: () -> Unit
) {
    val sharedViewModel: WriteJobViewModel = viewModel()
    NavHost(navController = navController, startDestination = WriteJobRoute.WRITE.route) {
        composable(WriteJobRoute.WRITE.route) {
            WriteJobScreen(viewModel = sharedViewModel, onBackClick = onBackClick,
                setAddress = {
                    navController.navigate(route = WriteShopRoute.ADDRESS.route)
                })
        }
        composable(WriteJobRoute.ADDRESS.route) {
            SetAddressScreen(updatelat = {lat->
                sharedViewModel.updateLat(lat)
            }, updatelng = {lng->
                sharedViewModel.updateLng(lng)
            }, updateaddress = {address->
                sharedViewModel.updateDetailAddress(address)
            }, finish = {
                navController.navigate(WriteJobRoute.WRITE.route)
            })
        }
    }
}