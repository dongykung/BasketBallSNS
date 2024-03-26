package com.dkproject.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dkproject.presentation.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopAppBarComponent(
    onCancelClick: () -> Unit,
) {
    TopAppBar(title = {},
        navigationIcon = {
            IconButton(onClick = onCancelClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription =
                    stringResource(id = R.string.cancel)
                )
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    title: String,
    onBack: Boolean = false, // 뒤로가기 버튼으 여부
    onBackClick: () -> Unit = {}, //뒤로가기 버튼이 클릭되었을 때
    actiontitle:String="",
    showAction: Boolean = false,//검색 버튼을 보여줄 것인가?
    actionClick: () -> Unit = {}, //검색 버튼클릭되었을 때
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(title = {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
    },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (onBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription =
                        stringResource(id = R.string.back)
                    )
                }
            }
        },
        actions = {
            if(showAction)
            TextButton(onClick = actionClick) {
                Text(text = actiontitle)
            }
        }
    )
}