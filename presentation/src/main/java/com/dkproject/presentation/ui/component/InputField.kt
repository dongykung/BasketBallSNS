package com.dkproject.presentation.ui.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.NextPlan
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType=KeyboardType.Text,
    imeAction: ImeAction= ImeAction.Done,
    trailingIcon:Boolean=false,
    trailingIconClick:()->Unit={},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange : (String)->Unit
) {
    OutlinedTextField(
        value = value, onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = labelId) },
        enabled = enabled,
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions,
        trailingIcon = {
            if(trailingIcon)
                IconButton(onClick = trailingIconClick) {
                    Icons.AutoMirrored.Filled.ArrowForward
                }
        }
    )
}