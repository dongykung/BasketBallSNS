package com.dkproject.presentation.ui.component.writeshop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GetImageButton(
    count: Int = 0,
    getImageClick: () -> Unit,
) {
    Surface(shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .clickable { getImageClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.CameraAlt, contentDescription = null,
                tint = Color.LightGray
            )
            Text(
                text = "${count}/5",
                color = Color.LightGray
            )
        }
    }
}