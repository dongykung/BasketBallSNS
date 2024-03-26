package com.dkproject.presentation.ui.component.writeshop

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun ImageBox(
    imageUrl: Uri,
    onCnacel: (Uri) -> Unit
) {
    Box(
        modifier = Modifier
            .size(86.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp))
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable { onCnacel(imageUrl) },
            imageVector = Icons.Default.Cancel, contentDescription = null,
            tint = Color.LightGray
        )
    }
}


@Composable
@Preview(showBackground = true)
fun ImageBoxPreview() {
    val uri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000020"
    BasketballSNSTheme {
        ImageBox(imageUrl = uri.toUri(), onCnacel = {})
    }
}
