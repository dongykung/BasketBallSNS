package com.dkproject.presentation.ui.component

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dkproject.presentation.navigation.home.HomeRoute


@Composable
fun HomeBottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: HomeRoute = navBackStackEntry?.destination?.route.let { currentRoute ->
        HomeRoute.values().find { route -> currentRoute == route.route }
    } ?: HomeRoute.HOME


    NavigationBar {
        val navigationItem = listOf(
            HomeRoute.HOME,
            HomeRoute.CHAT,
            HomeRoute.SHOP,
            HomeRoute.SETTING
        )
        navigationItem.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                selected = currentRoute == item,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        this.launchSingleTop = true
                        this.restoreState = true
                    }

                },
                icon = {
                    if (currentRoute == item)
                        Icon(imageVector = item.selecticon, contentDescription = item.description)
                    else
                        Icon(imageVector = item.icon, contentDescription = item.description)
                },
                label = {
                    Text(
                        text = item.description,
                        color = if (currentRoute == item) Color.Black else Color.Gray,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                })
        }
    }
}