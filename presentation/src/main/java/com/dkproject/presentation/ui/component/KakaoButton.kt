package com.dkproject.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R

@Composable
fun KakaoButton(
    modifier: Modifier=Modifier,
    onClick:()->Unit,
) {
    Surface(
    modifier = modifier.clickable { onClick() },
    shape = RoundedCornerShape(8.dp),
    color = Color.White
    ) {
      Image(painter = painterResource(id = R.drawable.kakao_login_large_wide), contentDescription = "kakao_login",
          modifier=Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop)
    }
}