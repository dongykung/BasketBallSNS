package com.dkproject.presentation.ui.screen.home.home.guest

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.home.Guest
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.home.GuestInfoSection
import com.dkproject.presentation.ui.component.home.WriterInfoSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestScreen(
    uid: String,
    viewModel: GuestViewModel,
    onBackClick: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val loading = viewModel.loading
    val error = viewModel.error
    Scaffold(topBar = {
        HomeTopAppBar(
            title = "",
            onBack = true,
            onBackClick = {
                onBackClick()
            }
        )
    },
        bottomBar = {
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)){
                Button(modifier=Modifier.weight(1f),
                    onClick = { /*TODO*/ }) {
                    Text(text = "신청하기")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(modifier=Modifier.weight(1f),
                    onClick = { /*TODO*/ }) {
                    Text(text = "채팅하기")
                }
            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (loading)
                LoadingScreen(modifier = Modifier.align(Alignment.Center))
            else if (error)
                ErrorScreen(modifier = Modifier.align(Alignment.Center),
                    retryButton = {
                        viewModel.getGuestItem(uid)
                    })
            else {
                Column(modifier= Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())) {
                    WriterInfoSection(user = state.userInfo)
                    HorizontalDivider(modifier=Modifier.padding(horizontal = 16.dp))
                    GuestInfoSection(modifier= Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),guest = state.guest)
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    retryButton: () -> Unit,
) {
    Button(modifier=modifier,
        onClick = { retryButton() }) {
        Text(text = "다시 시도")
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}


