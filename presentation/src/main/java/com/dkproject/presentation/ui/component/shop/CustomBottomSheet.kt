package com.dkproject.presentation.ui.component.shop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    showVisible: Boolean,
    valueList: List<String>,
    division:Boolean=true,
    divisionChange:(Boolean)->Unit={},
    onValueChange: (String) -> Unit = {},
    onDismiss: () -> Unit,
) {
    if (showVisible) {
        ModalBottomSheet(onDismissRequest = onDismiss) {
            LazyColumn {
                items(valueList) {
                    BottomSheetItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 18.dp)
                            .clickable {
                                onValueChange(it) },
                        text = it
                    )
                }
            }
        }
    }
}

@Composable
fun BottomSheetItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 18.sp, color = Color.Black)
        )
    }
}