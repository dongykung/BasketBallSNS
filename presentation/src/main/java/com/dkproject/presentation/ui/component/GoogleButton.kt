package com.dkproject.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp,color=Color.LightGray),
        color = Color.White
    ) {
        Row(modifier=Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier=Modifier.padding(start = 4.dp),
                painter = painterResource(id = R.drawable.googlelogo),
                contentDescription = stringResource(
                    id = R.string.google
                ),
                tint = Color.Unspecified
            )
            Text(modifier=Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.googleLogin),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleButtonPreview(){
    BasketballSNSTheme {
        GoogleButton(modifier=Modifier.fillMaxWidth()) {

        }
    }
}