package com.dkproject.presentation.ui.component.section

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.writeshop.InputPriceField

@Composable
 fun PriceSection(
    price: String,
    onPriceChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 12.dp, start = 3.dp),
        text = stringResource(id = R.string.price)
    )
    //inputField
    InputPriceField(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxSize(),
        price = price,
        labelId = stringResource(id = R.string.putprice),
        onPriceChange = {
            if (it.length <= 10)
                onPriceChange(it)
        }
    )
}