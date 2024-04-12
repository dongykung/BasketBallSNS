package com.dkproject.presentation.ui.component.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeNameBottomSheet(
    value:String,
    valueChange:(String)->Unit,
    onDismiss:()->Unit,
) {
    var nickValue by remember {
        mutableStateOf(value)
    }
    ModalBottomSheet(onDismissRequest = {onDismiss()}) {
        Text(text = "닉네임 변경",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = nickValue, onValueChange = {nickValue=it},modifier=Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier=Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = { valueChange(nickValue)}) {
            Text(text = "확인")
        }
    }

}