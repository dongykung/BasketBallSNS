package com.dkproject.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null,

) {

    Image(
        modifier = modifier
            .clip(CircleShape),
        painter = profileImageUrl?.let {
            rememberAsyncImagePainter(
                model = profileImageUrl,
                contentScale = ContentScale.Crop
            )
        } ?: rememberVectorPainter(image = Icons.Filled.Person),
        colorFilter = if (profileImageUrl == null) ColorFilter.tint(Color.White) else null,
        contentDescription = stringResource(id = R.string.profileImage),
        contentScale = ContentScale.Crop,
    )
}
