package com.dkproject.presentation.ui.component.writeshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun articleType(
    selectedType: String,
    typeChange: (String) -> Unit
) {

    val typeList = listOf<String>(
        stringResource(id = R.string.basketball),
        stringResource(id = R.string.safearticle),
        stringResource(id = R.string.shoes),
        stringResource(id = R.string.bag),
        stringResource(id = R.string.socks),
        stringResource(id = R.string.cloth)
    )
    FlowRow(
        modifier = Modifier.padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        typeList.forEach { type ->
            FilterChip(
                selected = selectedType == type,
                onClick = { typeChange(type) },
                label = {
                    Text(text = type)
                },
                colors = if(selectedType==type)
                    FilterChipDefaults.filterChipColors(containerColor = Color.LightGray)
                else
                    FilterChipDefaults.filterChipColors()
            )
        }
    }
}