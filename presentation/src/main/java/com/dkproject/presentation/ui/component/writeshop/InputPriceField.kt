package com.dkproject.presentation.ui.component.writeshop

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


@Composable
fun InputPriceField(
    modifier: Modifier=Modifier,
    price:String,
    enabled:Boolean=true,
    labelId:String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardType:KeyboardType=KeyboardType.Number,
    imeAction:ImeAction = ImeAction.Done,
    keyboardActions:KeyboardActions=KeyboardActions.Default,
    onPriceChange:(String)->Unit
){

    val krw = Currency.getInstance(Locale.KOREA).getSymbol()

    OutlinedTextField(
        value = price, onValueChange = {
            val trimmed = it.trimStart('0').trim{it.isDigit().not()}
            if(trimmed.isNotEmpty()&&trimmed.length<=10)
                onPriceChange(trimmed)
        },
        label = { Text(text = labelId) },
        enabled = enabled,
        singleLine = true,
        leadingIcon = {
                      Text(text = krw)
        },
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions,

    )
}