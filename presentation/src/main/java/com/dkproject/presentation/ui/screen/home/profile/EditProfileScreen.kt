package com.dkproject.presentation.ui.screen.home.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.ui.component.profile.ChangeNameBottomSheet
import com.dkproject.presentation.ui.screen.userfirst.PlayStyleFlow
import com.dkproject.presentation.ui.screen.userfirst.PositionFlow
import com.dkproject.presentation.ui.screen.userfirst.playSkillFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onBackClick: () -> Unit,
    onChangeClick:()->Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    var nameBottomSheet by remember {
        mutableStateOf(false)
    }

    val visualMediaPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.profileChange = true
                viewModel.updateUiState(state.userInfo.copy(profileImageUrl = uri.toString()))
            }
        }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "프로필 편집") },
            navigationIcon = {
                //프로필 수정 취소
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }, //프로필 수정 확인 버튼
            actions = {
                TextButton(onClick = {
                    viewModel.changeUserInfo(context) {
                        onChangeClick()
                    }
                }) {
                    Text(text = "확인")
                }
            })
    }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = state.userInfo.profileImageUrl),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(top = 6.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            visualMediaPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = state.userInfo.nickname,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .clickable { nameBottomSheet = true })
                }

                if (nameBottomSheet) {
                    ChangeNameBottomSheet(
                        value = state.userInfo.nickname,
                        valueChange = { nickname ->
                            viewModel.updateUiState(state.userInfo.copy(nickname = nickname))
                            nameBottomSheet = false
                        }) {//dissmiss
                        nameBottomSheet = false
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "포지션", modifier = Modifier.padding(start = 12.dp),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )

                //position Change Flow
                PositionFlow(selectList = state.userInfo.playPosition,
                    onPositionClick = { position ->
                        if (state.userInfo.playPosition.contains(position)) {
                            val list = state.userInfo.playPosition - position
                            viewModel.updateUiState(state.userInfo.copy(playPosition = list))
                        } else {
                            val list = state.userInfo.playPosition + position
                            viewModel.updateUiState(state.userInfo.copy(playPosition = list))
                        }
                    })

                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "플레이 스타일", modifier = Modifier.padding(start = 12.dp),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                //playStyle Chagne Flow
                PlayStyleFlow(selectedList = state.userInfo.playStyle) { playStyle ->
                    if (state.userInfo.playStyle.contains(playStyle)) {
                        val list = state.userInfo.playStyle - playStyle
                        viewModel.updateUiState(state.userInfo.copy(playStyle = list))
                    } else {
                        val list = state.userInfo.playStyle + playStyle
                        viewModel.updateUiState(state.userInfo.copy(playStyle = list))
                    }
                }

                //playSkill Change Flow
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "숙련도", modifier = Modifier.padding(start = 12.dp),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                playSkillFlow(selectedSkill = state.userInfo.userSkill) { skill ->
                    viewModel.updateUiState(state.userInfo.copy(userSkill = skill))
                }


            }
        }
    }
}