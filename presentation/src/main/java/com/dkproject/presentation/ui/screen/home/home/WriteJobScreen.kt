package com.dkproject.presentation.ui.screen.home.home

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.SettingCancelDialog
import com.dkproject.presentation.ui.component.section.AddressSection
import com.dkproject.presentation.ui.component.section.DateSection
import com.dkproject.presentation.ui.component.section.ExplainSection
import com.dkproject.presentation.ui.component.section.NumberSection
import com.dkproject.presentation.ui.component.section.TitleNameSection
import com.dkproject.presentation.ui.component.shop.CustomBottomSheet
import com.dkproject.presentation.ui.screen.userfirst.PositionFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteJobScreen(
    viewModel: WriteJobViewModel,
    onBackClick: () -> Unit,
    setAddress:()->Unit,
    onLoad:()->Unit,
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    if (visible)
        SettingCancelDialog(visible = visible, onCancelClick = { onBackClick() }) {
            visible = false
        }

    val state = viewModel.state.collectAsState().value
    val valid =
        state.title.isNotEmpty() && state.content.isNotEmpty() && state.count!=0
                && state.detailAddress.isNotEmpty()
                && state.time.isNotEmpty() && state.positionList.isNotEmpty()
    var loadingProgressBar by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        HomeTopAppBar(
            title = stringResource(id = R.string.job),
            onBack = true,
            onBackClick = {
                visible = true
            },
            showAction = true,
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(loadingProgressBar)
                CircularProgressIndicator(modifier=Modifier.align(Alignment.Center))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                //titleName section
                TitleNameSection(title = state.title) { title ->
                    viewModel.updateTitle(title)
                }
                NumberSection(number = state.count.toString()) {
                    viewModel.updateCount(it.toInt())
                }
                //position section
                PositionSection(
                    state.positionList,
                    positionChange = { position ->
                        viewModel.updatePositionList(position)
                    }
                )

                //explainSection
                ExplainSection(content = state.content) {content->
                    viewModel.updateContent(content)
                }

                DateSection(date = state.detaildate,
                    hour = state.time,
                    dateChange = {date->
                        viewModel.updatedaydate(date)
                        viewModel.updateDate(date)
                    },
                    timeChange = {time->
                        viewModel.updateTime(time)
                    },
                    hourChange = {hour->
                        viewModel.updateHour(hour)
                    },
                    minChange = {min->
                        viewModel.updateMin(min)
                    })

                AddressSection(address = state.detailAddress, setAddress = setAddress)

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 3.dp),
                    enabled = valid,
                    onClick = {
                        viewModel.uploadGuest(
                            success = onLoad,
                            loading ={loadingProgressBar=true},
                            failed = {loadingProgressBar=false}
                        )
                    }) {
                    Text(text = stringResource(id = R.string.upload))
                }
            }
        }
    }
}

@Composable
fun PositionSection(
    positionList: List<String>,
    positionChange: (String) -> Unit,
) {
    PositionFlow(selectList = positionList, onPositionClick = positionChange)
}
