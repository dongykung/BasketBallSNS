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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.ConfirmDialog
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.InputField
import com.dkproject.presentation.ui.component.SettingCancelDialog
import com.dkproject.presentation.ui.component.writeshop.ContentInputField
import com.dkproject.presentation.ui.component.writeshop.GetImageButton
import com.dkproject.presentation.ui.component.writeshop.ImageBox
import com.dkproject.presentation.ui.component.writeshop.InputPriceField
import com.dkproject.presentation.ui.component.writeshop.articleType
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteShopScreen(
    viewModel: WriteShopViewModel,
    setAddress: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val valid =
        state.name.isNotEmpty() && state.price.isNotEmpty() && state.content.isNotEmpty() && state.detailAddress.isNotEmpty()
                && state.type.isNotEmpty() && state.imageList.isNotEmpty()
    val visualMediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uris ->
        if (uris.isNotEmpty())
            uris.forEach { uri ->
                viewModel.addImageList(uri)
            }
    }
    var visible by rememberSaveable { mutableStateOf(false) }
    if (visible)
        SettingCancelDialog(visible = visible, onCancelClick = { onBackClick() }) {
            visible = false
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
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Divider()

                //article Image Section
                ImagePickerSection(
                    selectedImageList = state.imageList,
                    onAddImage = {
                        visualMediaPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    onRemoveImage = { uri ->
                        viewModel.removeImageList(uri)
                    })

                //ArticleType Section
                ArticleTypeSection(type = state.type, typeChange = { type ->
                    viewModel.updateType(type)
                })

                //TitleSection
                TitleNameSection(title = state.name, onTitleChange = { name ->
                    viewModel.updateName(name)
                })

                //PriceSection
                PriceSection(
                    price = state.price,
                    onPriceChange = { price ->
                        viewModel.updatePrice(price)
                    }
                )

                //explainSection
                ExplainSection(content = state.content, onContentChange = { content ->
                    viewModel.updateContent(content)
                })

                //addressSection
                AddressSection(address = state.detailAddress, setAddress = setAddress)


                //addressSection

            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 3.dp),
                enabled = valid,
                onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.sale))
            }
        }
    }
}


@Composable
private fun ImagePickerSection(
    selectedImageList: List<Uri>,
    onAddImage: () -> Unit,
    onRemoveImage: (Uri) -> Unit
) {
    // 이미지 선택 및 삭제 관련 UI 구성
    Row(
        modifier = Modifier
            .padding(top = 12.dp)
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //add imagebutton
        GetImageButton(selectedImageList.size, getImageClick = onAddImage)
        //get image
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedImageList) { uri ->
                ImageBox(imageUrl = uri, onCnacel = {
                    onRemoveImage(it)
                })
            }
        }
    }
}

@Composable
private fun ArticleTypeSection(
    type: String,
    typeChange: (String) -> Unit
) {
    articleType(selectedType = type, typeChange = typeChange)
}

@Composable
private fun TitleNameSection(
    title: String,
    onTitleChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 6.dp, start = 3.dp),
        text = stringResource(id = R.string.title)
    )
    //inputField
    InputField(modifier = Modifier
        .padding(horizontal = 5.dp)
        .fillMaxSize(),
        value = title, labelId = "",
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        onValueChange = {
            if (it.length <= 20)
                onTitleChange(it)
        })
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 5.dp),
        text = stringResource(id = R.string.maxNickname, title.length),
        textAlign = TextAlign.End
    )
}

@Composable
private fun PriceSection(
    price: String,
    onPriceChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 12.dp, start = 3.dp),
        text = stringResource(id = R.string.price)
    )
    //inputField
    InputPriceField(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxSize(),
        price = price,
        labelId = stringResource(id = R.string.putprice),
        onPriceChange = {
            if (it.length <= 10)
                onPriceChange(it)
        }
    )
}


@Composable
private fun ExplainSection(
    content: String,
    onContentChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 12.dp, start = 3.dp, bottom = 3.dp),
        text = stringResource(id = R.string.content)
    )
    ContentInputField(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .height(150.dp),
        value = content, valueChange = onContentChange
    )
}

@Composable
private fun AddressSection(
    address: String,
    setAddress: () -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 12.dp, start = 3.dp, bottom = 3.dp),
        text = stringResource(id = R.string.address)
    )
    InputField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 3.dp)
            .clickable { setAddress() },
        value = address,
        labelId = "",
        trailingIcon = true,
        trailingIconClick = setAddress,
        onValueChange = {},
        enabled = false
    )
}

