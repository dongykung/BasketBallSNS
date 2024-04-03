package com.dkproject.presentation.ui.component.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateBottomSheet(
    modifier: Modifier=Modifier,
    visible: Boolean,
    dismiss: () -> Unit,
    dateChange: (Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    var skipPartiallyExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    if (visible) {
        val windowInsets = BottomSheetDefaults.windowInsets
        ModalBottomSheet(modifier=modifier,
            sheetState = bottomSheetState,
            windowInsets = windowInsets,
            onDismissRequest = { dismiss() }) {
                DatePicker(
                    state = datePickerState,
                    title = {
                        Text(
                            text = "날짜 정하기",
                            modifier = Modifier.padding(start = 12.dp, top = 10.dp)
                        )
                    },
                )
                Row {
                    TextButton(onClick = { dismiss() }) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = {
                        if (datePickerState.selectedDateMillis != null) {
                            dateChange(datePickerState.selectedDateMillis!!)
                            dismiss()
                        }
                    }) {
                        Text(text = "OK")
                    }
                }
            }
    }
}
