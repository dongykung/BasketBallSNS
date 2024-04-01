package com.dkproject.presentation.ui.component.section

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.InputField

@Composable
 fun TitleNameSection(
    title: String,
    onTitleChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 6.dp, start = 3.dp),
        text = stringResource(id = R.string.title)
    )
    //inputField
    InputField(modifier = Modifier
        .padding(horizontal = 5.dp)
        .fillMaxSize(),
        value = title, labelId = "",
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        onValueChange = {
            if (it.length <= 20)
                onTitleChange(it)
        })
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 5.dp),
        text = stringResource(id = R.string.maxNickname, title.length),
        textAlign = TextAlign.End
    )
}