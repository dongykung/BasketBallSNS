package com.dkproject.presentation.ui.component.section

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.writeshop.ContentInputField

@Composable
 fun ExplainSection(
    content: String,
    onContentChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 12.dp, start = 3.dp, bottom = 3.dp),
        text = stringResource(id = R.string.content)
    )
    ContentInputField(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .height(150.dp),
        value = content, valueChange = onContentChange
    )
}