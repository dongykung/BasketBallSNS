package com.dkproject.presentation.navigation.home

import android.Manifest
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.CallMade
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SportsBasketball
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkproject.presentation.ui.activity.WriteShopActivity
import com.dkproject.presentation.ui.component.HomeBottomNavigationBar
import com.dkproject.presentation.ui.component.showToastMessage
import com.dkproject.presentation.ui.screen.home.chat.ChatScreen
import com.dkproject.presentation.ui.screen.home.club.ClubScreen
import com.dkproject.presentation.ui.screen.home.home.HomeScreen
import com.dkproject.presentation.ui.screen.home.profile.ProfileScreen
import com.dkproject.presentation.ui.screen.home.shop.ShopScreen

enum class HomeRoute(
    val route: String,
    val selecticon: ImageVector,
    val icon: ImageVector,
    val description: String
) {
    HOME("HomeScreen", Icons.Filled.SportsBasketball, Icons.Outlined.SportsBasketball, "홈"),
    CLUB("ClubScreen", Icons.Filled.Person, Icons.Outlined.Person, "모임"),
    CHAT("ChatScreen", Icons.AutoMirrored.Filled.Chat, Icons.AutoMirrored.Outlined.Chat, "채팅"),
    SHOP("ShopScreen", Icons.Filled.AddShoppingCart, Icons.Outlined.AddShoppingCart, "거래"),
    SETTING("ProfileScreen", Icons.Filled.Settings, Icons.Outlined.Settings, "프로필");

}

@Composable
fun HomeNavigation(navController: NavHostController = rememberNavController()) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            HomeBottomNavigationBar(navController = navController)
        }) { innerPadding ->
        HomeNavigationScreen(navController, innerPadding)
    }
}

@Composable
fun HomeNavigationScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()) {permissions->
           when{
               permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false)->{
                   context.startActivity(Intent(context,WriteShopActivity::class.java))
               }
               permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false)->{
                   context.startActivity(Intent(context,WriteShopActivity::class.java))
               }
               else->{
                   showToastMessage(context,"위치 권한을 허용해주세요")
               }
           }
        }
    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = HomeRoute.HOME.route
    ) {
        composable(route = HomeRoute.HOME.route) {
            HomeScreen()
        }
        composable(route = HomeRoute.CLUB.route) {
            ClubScreen()
        }
        composable(route = HomeRoute.CHAT.route) {
            ChatScreen()
        }
        composable(route = HomeRoute.SHOP.route) {
            ShopScreen(onWriteClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            })
        }
        composable(route = HomeRoute.SETTING.route) {
            ProfileScreen()
        }
    }
}
