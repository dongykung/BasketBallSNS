package com.dkproject.presentation.ui.screen.userfirst

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.component.PreviousNextButton
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun PlayPositionScreen(
    viewModel: UserFirstViewModel,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val state = viewModel.state.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        //position text
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.userposition),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(40.dp))

        //position flow chip
        PositionFlow(selectList = state.playPosition,
            onPositionClick = {
                viewModel.updateUserPlayPosition(it)
            })
        Spacer(modifier = Modifier.height(20.dp))

        //style text
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.userplaystyle),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        //style flow chip
        PlayStyleFlow(
            selectedList = state.playStyle,
            onPositionClick = {
                viewModel.updateUserPlayStyle(it)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.userplaystyle),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        playSkillFlow(
            selectedSkill = state.userSkill,
            onSkillChange = {
                viewModel.updateUserSkill(it)
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        //button
        PreviousNextButton(
            onPrevClick = onPrevClick, onNextClick = onNextClick, enabled =
            state.playPosition.isNotEmpty() && state.playStyle.isNotEmpty() && state.userSkill.isNotEmpty()
        )


    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PositionFlow(
    selectList: List<String>,
    onPositionClick: (String) -> Unit
) {

    val positionList: List<String> = listOf(
        stringResource(id = R.string.PG),
        stringResource(id = R.string.SG),
        stringResource(id = R.string.SF),
        stringResource(id = R.string.PF),
        stringResource(id = R.string.CC)
    )
    FlowRow() {
        positionList.forEach {
            val selected = selectList.contains(it)
            FilterChip(modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                selected = selected,
                onClick = {
                    onPositionClick(it)
                },
                label = { Text(text = it) },
                leadingIcon = {
                    if (selected) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(id = R.string.check)
                        )
                    }
                })
        }
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayStyleFlow(
    selectedList: List<String>,
    onPositionClick: (String) -> Unit
) {
    val styleList: List<String> = listOf(
        stringResource(id = R.string.leading),
        stringResource(id = R.string.passing),
        stringResource(id = R.string.middleshooting),
        stringResource(id = R.string.farshooting),
        stringResource(id = R.string.breakthrough),
        stringResource(id = R.string.dribble),
        stringResource(id = R.string.speed),
        stringResource(id = R.string.pattern),
        stringResource(id = R.string.defense),
        stringResource(id = R.string.undergoal),
        stringResource(id = R.string.rebound),
        stringResource(id = R.string.postup),
        stringResource(id = R.string.screen),
        stringResource(id = R.string.funny)
    )
    FlowRow() {
        styleList.forEach {
            val selected = selectedList.contains(it)
            FilterChip(modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                selected = selected,
                onClick = {
                    onPositionClick(it)
                },
                label = { Text(text = it) },
                leadingIcon = {
                    if (selected) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(id = R.string.check)
                        )
                    }
                })
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun playSkillFlow(
    selectedSkill: String,
    onSkillChange: (String) -> Unit
) {
    val skillList = listOf<String>(
        stringResource(id = R.string.grow),
        stringResource(id = R.string.basic),
        stringResource(id = R.string.middle),
        stringResource(id = R.string.master)
    )
    FlowRow() {
        skillList.forEach {
            val selected = selectedSkill.equals(it)
            FilterChip(modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                selected = selected,
                onClick = {
                    onSkillChange(it)
                },
                label = { Text(text = it) },
                leadingIcon = {
                    if (selected) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(id = R.string.check)
                        )
                    }
                })
        }
    }
}


