package com.dkproject.presentation.ui.screen.home.shop

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.ui.activity.ChatActivity
import com.dkproject.presentation.ui.activity.UserProfileActivity
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.home.WriterInfoSection
import com.dkproject.presentation.ui.component.section.ArticleInfoSection
import com.dkproject.presentation.ui.component.section.MapSection
import com.dkproject.presentation.ui.component.shop.ImagePager
import com.dkproject.presentation.ui.screen.home.home.guest.ErrorScreen
import com.dkproject.presentation.ui.screen.home.home.guest.LoadingScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailScreen(
    viewModel: ShopDetailViewModel,
    shopUid: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val loading = viewModel.loading
    val error = viewModel.error
    Scaffold(topBar = {
        HomeTopAppBar(
            title = state.articleInfo.name,
            onBack = true,
            onBackClick = onBackClick
        )
    },
        bottomBar = {
            Button(modifier=Modifier.padding(horizontal = 22.dp).fillMaxWidth(),onClick = {
                context.startActivity(Intent(context, ChatActivity::class.java).apply {
                    putExtra("UserUid", state.articleInfo.writerUid)
                })
            }) {
                Text(text = "채팅하기")
            }
        }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (loading)
                LoadingScreen()
            else if (error)
                ErrorScreen(modifier = Modifier.align(Alignment.Center),
                    retryButton = {
                        viewModel.getArticle(shopUid)
                    })
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    //image Pager
                    ImagePager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        imageList = state.articleInfo.imageList
                    )

                    //writer Info Section
                    WriterInfoSection(
                        modifier=Modifier.padding(horizontal = 16.dp),
                        user = state.userInfo){uid->
                        context.startActivity(Intent(context, UserProfileActivity::class.java).apply {
                            putExtra("userUid",uid)
                        })
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    HorizontalDivider(thickness = 5.dp)
                    //Article Info Section
                    ArticleInfoSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        article = state.articleInfo
                    )
                    HorizontalDivider(thickness = 5.dp)

                    Spacer(modifier = Modifier.height(18.dp))
                    MapSection(
                        modifier=Modifier.padding(horizontal = 16.dp),
                        lat = state.articleInfo.lat,
                        lng = state.articleInfo.lng,
                        context = context,
                        detailAddress = state.articleInfo.detailAddress
                    )

                }
            }
        }
    }
}


