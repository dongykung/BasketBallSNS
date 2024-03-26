package com.dkproject.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun HomeFloatingButton(
    title: String,
    onClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        modifier= Modifier.padding(bottom = 3.dp),
        shape = RoundedCornerShape(28.dp),
        text = { Text(text = title) },
        icon = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.write)
            )
        },
        onClick = onClick)
}

@Composable
@Preview
fun FloatingPreview(){
    BasketballSNSTheme {
        HomeFloatingButton(title = "글쓰기") {
            
        }
    }
}