package com.dkproject.presentation.ui.component.writeshop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun ContentInputField(
    modifier: Modifier = Modifier,
    value: String,
    valueChange: (String) -> Unit,
) {
    val scrollModifier = Modifier.verticalScroll(rememberScrollState())
    OutlinedTextField(
        modifier=modifier.then(scrollModifier),
        value = value,
        onValueChange ={
            if(it.length<=100)
                valueChange(it)
        },
        textStyle = MaterialTheme.typography.bodyLarge
    )
}

@Composable
@Preview(showBackground = true)
fun DefaultInputFieldPreview(){
    BasketballSNSTheme {
        val text = remember {
            mutableStateOf("")
        }
        Column(modifier=Modifier.fillMaxSize()) {
            ContentInputField(modifier=Modifier.height(50.dp).fillMaxWidth(),
                value = text.value, valueChange = {text.value=it})
        }

    }
}