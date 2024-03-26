package com.dkproject.presentation.ui.screen.home.shop

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.ConfirmDialog
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.writeshop.GetImageButton
import com.dkproject.presentation.ui.component.writeshop.ImageBox
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun WriteShopScreen(
    viewModel: WriteShopViewModel = hiltViewModel(),
    onCancel: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val visualMediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(5)
    ) {uris->
        if(uris.isNotEmpty())
            uris.forEach{uri->
                viewModel.addImageList(uri)
            }
    }
    WriteShopScreen(
        state.imageList,
        onBackClick = onCancel,
        onaddImage = {
            visualMediaPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        onRemoveClick = {uri->
            viewModel.removeImageList(uri)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteShopScreen(
    selectedImageList: List<Uri>,
    onBackClick: () -> Unit,
    onaddImage:()->Unit,
    onRemoveClick:(Uri)->Unit
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    if (visible)
        ConfirmDialog(text = stringResource(id = R.string.cancelcell), visible = visible) {
            visible = false
            onBackClick()
        }
    Scaffold(topBar = {
        HomeTopAppBar(
            title = stringResource(id = R.string.cell),
            onBack = true,
            onBackClick = {
                visible = true
            },
            showAction = true,
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 9.dp)
        ) {
            Divider()
            Row(
                modifier = Modifier.padding(top = 12.dp).height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GetImageButton(selectedImageList.size, getImageClick =onaddImage)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(selectedImageList){uri->
                        ImageBox(imageUrl = uri, onCnacel = {
                            onRemoveClick(it)
                        })
                    }
                }
            }

        }
    }
}



