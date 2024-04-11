package com.dkproject.presentation.ui.component.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PositionComponent(
    position:String
) {
    Surface(shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.secondaryContainer) {
        Text(text = position,
            style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 15.sp),
            modifier=Modifier.padding(12.dp)
        )
    }
}