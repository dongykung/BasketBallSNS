package com.dkproject.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun refreshbutton(
    modifier: Modifier=Modifier,
    click:()->Unit,
) {
    Surface(border = BorderStroke(1.dp,color= Color.LightGray),
        shape = CircleShape) {
            IconButton(onClick = click) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh")
            }

    }
}