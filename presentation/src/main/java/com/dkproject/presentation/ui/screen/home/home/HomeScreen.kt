package com.dkproject.presentation.ui.screen.home.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dkproject.presentation.R
import com.dkproject.presentation.model.ShopUiModel
import com.dkproject.presentation.ui.component.HomeFloatingButton
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.home.CustomDateBottomSheet
import com.dkproject.presentation.ui.component.shop.CustomBottomSheet
import com.dkproject.presentation.ui.component.shop.DivisionChip
import com.dkproject.presentation.ui.screen.home.shop.ShopCard
import com.dkproject.presentation.util.moveToSettingDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onWriteClick: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val items: LazyPagingItems<GuestUiModel> = state.itemList.collectAsLazyPagingItems()

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopAppBar(
                title = stringResource(id = R.string.guest),
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            HomeFloatingButton(title = stringResource(id = R.string.write)) {
                onWriteClick()
            }
        }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            HomeDivisionSection(positionValue = state.position,
                distance = state.distacne,
                onPositionChange = { position ->
                    viewModel.updatePosition(position)
                }, distanceChange = { distance ->
                    viewModel.updateDistacne(distance)
                })
            HomeItemList(items)
        }
    }
}

@Composable
private fun HomeItemList(
    homeItemList: LazyPagingItems<GuestUiModel>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        items(
            count = homeItemList.itemCount,
            key = { index ->
                homeItemList.get(index)?.uid ?: index
            }
        ) { index ->
            homeItemList[index]?.run {
                HomeItemCard(item = this)
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp))
            }
        }
    }
}


@Composable
fun HomeDivisionSection(
    modifier: Modifier = Modifier,
    positionValue: String,
    distance: Boolean,
    onPositionChange: (String) -> Unit,
    distanceChange: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    var isPositionBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val position = listOf("모두보기", "포인트 가드", "슈팅 가드", "파워 포워드", "스몰 포워드", "센터")
    
    var datetext by remember {
        mutableStateOf("날짜")
    }
    var isDateBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    if(isDateBottomSheetVisible){
        CustomDateBottomSheet(modifier=Modifier.fillMaxHeight(),
            visible = isDateBottomSheetVisible, dismiss = { isDateBottomSheetVisible=false}) {

        }
    }
    if (isPositionBottomSheetVisible) {
        CustomBottomSheet(showVisible = isPositionBottomSheetVisible,
            valueList = position,
            onValueChange = {
                isPositionBottomSheetVisible = false
                onPositionChange(it)
            },
            onDismiss = { isPositionBottomSheetVisible = false })
    }
   
    var permissionDialog by remember { mutableStateOf(false) }
    if (permissionDialog)
        moveToSettingDialog(context, permissionDialog, changeOpenDialog = {
            permissionDialog = it
        })
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    distanceChange(!distance)
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    distanceChange(!distance)
                }

                else -> {
                    permissionDialog = true
                }
            }
        }
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DivisionChip(
            modifier = Modifier.padding(start = 12.dp),
            arrow = true,
            value = positionValue,
            onClick = {
                isPositionBottomSheetVisible = true
            })
        
        DivisionChip(value = datetext, onClick = {
            isDateBottomSheetVisible=true
        })
        DivisionChip(value = "내 주변 보기", arrow = false, distance = distance, onClick = {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        )
    }

}