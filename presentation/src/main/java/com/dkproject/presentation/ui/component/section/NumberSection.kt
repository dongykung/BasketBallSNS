package com.dkproject.presentation.ui.component.section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.InputField
import com.dkproject.presentation.ui.component.shop.CustomBottomSheet

@Composable
fun NumberSection(
    modifier: Modifier = Modifier,
    number: String,
    numberChange: (String) -> Unit,
) {
    var visible = remember {
        mutableStateOf(false)
    }
    val countList: MutableList<String> = ArrayList()
    for (i in 1..20)
        countList.add(i.toString())
    if(visible.value){
        CustomBottomSheet(showVisible = visible.value, valueList = countList,
            onDismiss = {visible.value=false},
            onValueChange = {
                visible.value=false
                numberChange(it)
            })
    }
    Text(text = stringResource(id = R.string.count))
    Surface(
        modifier = modifier.clickable {visible.value=true},
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = number + "명",
                modifier = Modifier.padding(2.dp)
            )
        }

    }
}


