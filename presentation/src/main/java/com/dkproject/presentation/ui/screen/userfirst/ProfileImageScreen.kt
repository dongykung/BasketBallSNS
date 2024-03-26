package com.dkproject.presentation.ui.screen.userfirst

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.PreviousNextButton
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun ProfileImageScreen(
    modifier: Modifier = Modifier,
    viewModel: UserFirstViewModel,
    onPrevClick:()->Unit,
    onNextClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    val visualMediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            viewModel.updateProfileImage(it.toString())
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        //profile text
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.explainprofile),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(40.dp))
        //user profileImageView
        Image(modifier= Modifier
            .size(180.dp)
            .align(Alignment.CenterHorizontally)
            .clip(CircleShape)
            .clickable {
                visualMediaPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            painter = rememberAsyncImagePainter(
                model = if (state.profileImageUrl.isNotEmpty()) state.profileImageUrl
                else R.drawable.default_image
            ), contentDescription = stringResource(id = R.string.profileImage)
        , contentScale = ContentScale.Crop)
        //TextButton - Change Image
        TextButton(modifier=Modifier.align(Alignment.CenterHorizontally)
            ,onClick = {
                visualMediaPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }) {
            Text(text = stringResource(id = R.string.changeImage))
        }

        Spacer(modifier = Modifier.weight(1f))
        //ButtonRow

        PreviousNextButton(onPrevClick = onPrevClick, onNextClick = onNextClick)
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileImageScreenPreview() {
    BasketballSNSTheme {
        ProfileImageScreen(onNextClick = {}, onPrevClick = {}, viewModel = viewModel())
    }
}