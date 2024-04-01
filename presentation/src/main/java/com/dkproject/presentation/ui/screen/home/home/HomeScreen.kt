package com.dkproject.presentation.ui.screen.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.HomeFloatingButton
import com.dkproject.presentation.ui.component.HomeTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onWriteClick:()->Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier=Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        HomeTopAppBar(
            title = stringResource(id = R.string.guest),
            scrollBehavior = scrollBehavior
        )
    },
        floatingActionButton = {
            HomeFloatingButton(title = stringResource(id = R.string.write)) {
                onWriteClick()
            }
        }) {innerPadding->
        Column(modifier=Modifier.padding(innerPadding)) {

        }
    }
}