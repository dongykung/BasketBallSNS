package com.dkproject.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dkproject.presentation.R


@Composable
fun SettingCancelDialog(
    visible:Boolean,
    ButtonSection:Boolean=true,
    onCancelClick:()->Unit,
    onDismissRequest :()->Unit
){
    if(visible){
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(modifier= Modifier.clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.primaryContainer) {
                Column(modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                 Text(modifier=Modifier.fillMaxWidth().padding(top=8.dp),
                     text = stringResource(id = R.string.settingCancel),
                     style = MaterialTheme.typography.bodyLarge,
                     textAlign = TextAlign.Center)

                    Text(modifier=Modifier.padding(12.dp).fillMaxWidth(),
                        text = stringResource(id = R.string.settingCancel2),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center)

                    Row {
                        TextButton(modifier= Modifier.weight(1f),onClick = {onDismissRequest() }) {
                            Text(text = "취소")
                        }
                        TextButton(modifier= Modifier.weight(1f),onClick = { onCancelClick()}) {
                            Text(text = "종료")
                        }
                    }
                }
            }
        }
    }
}