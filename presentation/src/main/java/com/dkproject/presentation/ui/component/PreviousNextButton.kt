package com.dkproject.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R


@Composable
fun PreviousNextButton(
    onPrevClick:()->Unit,
    onNextClick:()->Unit,
    enabled : Boolean=true
) {
    Row(modifier= Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        //PreviousButton
        Button(modifier= Modifier
            .weight(3f)
            .padding(horizontal = 3.dp),
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            onClick =onPrevClick) {
            Text(text = stringResource(id = R.string.previous))
        }
        //NextButtopn
        Button(modifier= Modifier
            .weight(7f)
            .padding(3.dp),
            enabled = enabled,
            onClick = onNextClick) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}