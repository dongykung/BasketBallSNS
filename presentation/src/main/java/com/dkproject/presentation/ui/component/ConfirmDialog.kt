package com.dkproject.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ConfirmDialog(
    text:String,
    visible:Boolean,
    onDismissRequest :()->Unit
){
    if(visible){
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(modifier= Modifier.clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.primaryContainer) {
                Column(modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(modifier= Modifier.padding(12.dp).fillMaxWidth(),
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center)
                    Row {
                        TextButton(modifier= Modifier.weight(1f),onClick = { onDismissRequest()}) {
                            Text(text = "확인")
                        }
                    }
                }
            }
        }
    }
}