package com.dkproject.presentation.ui.component.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun DivisionChip(
    modifier: Modifier=Modifier,
    value:String,
    onClick:()->Unit={}
) {
    Surface(
        modifier=modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(modifier=Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = value)
            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "")
        }
    }
}



@Composable
@Preview(showBackground = true)
fun DivisionChipPreview(){
    BasketballSNSTheme {
        DivisionChip(value = "카테고리")
    }
}