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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.ConfirmDialog
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.InputField
import com.dkproject.presentation.ui.component.SettingCancelDialog
import com.dkproject.presentation.ui.component.section.AddressSection
import com.dkproject.presentation.ui.component.section.ExplainSection
import com.dkproject.presentation.ui.component.section.ImagePickerSection
import com.dkproject.presentation.ui.component.section.PriceSection
import com.dkproject.presentation.ui.component.section.TitleNameSection
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
    onLoad:()->Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val valid =
        state.name.isNotEmpty() && state.price.isNotEmpty() && state.content.isNotEmpty() && state.detailAddress.isNotEmpty()
                && state.type.isNotEmpty() && state.imageList.isNotEmpty()
    val loading = viewModel.loading
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
        Box(modifier = Modifier.fillMaxSize()) {
            if(loading.value) CircularProgressIndicator(modifier=Modifier.align(Alignment.Center))
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
                    onClick = {
                        viewModel.uploadArticle(context, onLoad = onLoad)
                    }) {
                    Text(text = stringResource(id = R.string.sale))
                }
            }
        }
    }
}




@Composable
private fun ArticleTypeSection(
    type: String,
    typeChange: (String) -> Unit
) {
    val typeList = listOf<String>(
        stringResource(id = R.string.basketball),
        stringResource(id = R.string.safearticle),
        stringResource(id = R.string.shoes),
        stringResource(id = R.string.bag),
        stringResource(id = R.string.socks),
        stringResource(id = R.string.cloth)
    )
    articleType(selectedType = type, typeList = typeList, typeChange = typeChange)
}
