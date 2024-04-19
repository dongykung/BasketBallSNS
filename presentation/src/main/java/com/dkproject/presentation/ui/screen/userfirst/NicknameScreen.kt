package com.dkproject.presentation.ui.screen.userfirst

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.ConfirmDialog
import com.dkproject.presentation.ui.component.InputField
import com.dkproject.presentation.ui.component.SettingCancelDialog
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun NicknameScreen(
    modifier: Modifier = Modifier,
    viewModel: UserFirstViewModel,
    onNextClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    var visible by rememberSaveable { mutableStateOf(false) }
    ConfirmDialog(text = stringResource(id = R.string.duplicationNick), visible = visible) {
        visible = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //EditNickNameSection
        NicknameSection(state.nickname, onNickNameChange = {
            viewModel.updateNickname(it)
        })
        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            onClick = {
                viewModel.checkNickname(state.nickname,
                    moveToNext = onNextClick,
                    duplicatedNick = { visible = true })
            },
            enabled = state.nickname.isNotEmpty()
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@Composable
fun NicknameSection(
    text:String,
    onNickNameChange:(String)->Unit
){
    Text(
        modifier = Modifier.padding(12.dp),
        text = stringResource(id = R.string.inputNickname),
        style = MaterialTheme.typography.headlineLarge
    )

    Spacer(modifier = Modifier.height(100.dp))
    InputField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        value = text,
        labelId = "닉네임",
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        onValueChange = {
            if (it.length <= 20)
                onNickNameChange(it)
        }
    )
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.explainNickname),
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = stringResource(id = R.string.maxNickname, text.length))
    }
}

@Preview(showBackground = true)
@Composable
fun NicknameScreenPreview() {
    BasketballSNSTheme {
        NicknameScreen(viewModel = viewModel()) {

        }
    }
}