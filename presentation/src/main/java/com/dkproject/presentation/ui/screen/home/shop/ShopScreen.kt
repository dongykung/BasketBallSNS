package com.dkproject.presentation.ui.screen.home.shop

import android.Manifest
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dkproject.presentation.R
import com.dkproject.presentation.model.ShopUiModel
import com.dkproject.presentation.ui.activity.ShopDetailActivity
import com.dkproject.presentation.ui.activity.WriteShopActivity
import com.dkproject.presentation.ui.component.HomeFloatingButton
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.shop.CustomBottomSheet
import com.dkproject.presentation.ui.component.shop.DivisionChip
import com.dkproject.presentation.ui.component.showToastMessage
import com.dkproject.presentation.util.LocationPermission
import com.dkproject.presentation.util.moveToSettingDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    viewModel: ShopHomeViewModel,
    onWriteClick: () -> Unit,
    clickItem: (String) -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val items: LazyPagingItems<ShopUiModel> = state.shopList.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        HomeTopAppBar(
            title = stringResource(id = R.string.market),
            scrollBehavior = scrollBehavior
        )
    },
        floatingActionButton = {
            HomeFloatingButton(title = stringResource(id = R.string.write)) {
                onWriteClick()
            }
        }) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            DivisionSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                categoryText = state.type,
                distance = state.distanceOrder,
                divisionChange = {
                    viewModel.updatePrice(it)
                },
                distanceChange = {
                    viewModel.updateDistance(it)
                },
                categoryChange = {
                    viewModel.categoryChange(it)
                }
            )
            ShopListSection(shopCardModels = items, clickItem = clickItem)
        }
    }
}

@Composable
fun ShopListSection(
    shopCardModels: LazyPagingItems<ShopUiModel>,
    clickItem:(String)->Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = shopCardModels.itemCount,
//            key = { index ->
//                shopCardModels.get(index)?.uid ?: index
//            }
        ) { index ->
            shopCardModels[index]?.run {
                ShopCard(
                    modifier=Modifier.clickable { clickItem(this.uid) },
                    profileImage = this.image,
                    name = this.name,
                    address = this.detailAddress,
                    type = this.type,
                    price = this.price
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp))

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionSection(
    modifier: Modifier = Modifier,
    categoryText: String,
    distance: Boolean,
    distanceChange: (Boolean) -> Unit,
    divisionChange: (Boolean) -> Unit,
    categoryChange: (String) -> Unit,
) {
    val context = LocalContext.current
    var isCategoryBottomSheetVisible by remember { mutableStateOf(false) }
    val categories = listOf("모두보기", "농구화", "농구공", "보호대", "가방", "양말", "의류")
    var isPriceOrderBottomSheetVisible by remember { mutableStateOf(false) }
    val prices = listOf("높은 순", "낮은 순")

    var pricetext by remember {
        mutableStateOf("가격")
    }
    var permissionDialog by remember { mutableStateOf(false) }
    if(permissionDialog)
        moveToSettingDialog(context,permissionDialog, changeOpenDialog = {
            permissionDialog=it
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
            value = categoryText,
            onClick = {
                isCategoryBottomSheetVisible = true
            })
        DivisionChip(value = pricetext, arrow = true, onClick = {
            isPriceOrderBottomSheetVisible = true
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

        if (isCategoryBottomSheetVisible) {
            CustomBottomSheet(showVisible = isCategoryBottomSheetVisible,
                valueList = categories,
                onValueChange = {
                    isCategoryBottomSheetVisible = false
                    categoryChange(it)
                },
                onDismiss = { isCategoryBottomSheetVisible = false })
        }
        if (isPriceOrderBottomSheetVisible) {
            CustomBottomSheet(showVisible = isPriceOrderBottomSheetVisible,
                valueList = prices,
                onValueChange = { value ->
                    if (value.equals("높은 순")) {
                        isPriceOrderBottomSheetVisible = false
                        pricetext = value
                        divisionChange(false)
                    } else {
                        isPriceOrderBottomSheetVisible = false
                        pricetext = value
                        divisionChange(true)
                    }
                },
                onDismiss = { isPriceOrderBottomSheetVisible = false })
        }

    }
}

