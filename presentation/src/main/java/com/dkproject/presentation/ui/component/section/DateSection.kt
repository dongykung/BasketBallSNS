package com.dkproject.presentation.ui.component.section

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import java.text.SimpleDateFormat
import java.util.Locale


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSection(
    modifier: Modifier = Modifier,
    date: Long,
    hour: String,
    dateChange: (Long) -> Unit,
    hourChange:(String)->Unit,
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0,
        is24Hour = false
    )
    val showDateDialog = rememberSaveable {
        mutableStateOf(false)
    }
    val showTimeDialog = rememberSaveable {
        mutableStateOf(false)
    }

    val dateFormatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    if (showDateDialog.value) {
        DatePickerDialog(onDismissRequest = { showDateDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    if (datePickerState.selectedDateMillis != null) {
                        dateChange(datePickerState.selectedDateMillis!!)
                        showDateDialog.value = false
                    }
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDateDialog.value = false }) {
                    Text(text = "Cancel")
                }
            }) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "날짜 정하기",
                        modifier = Modifier.padding(start = 12.dp, top = 10.dp)
                    )
                },
            )
        }
    }
    if (showTimeDialog.value) {
        Dialog(onDismissRequest = { showTimeDialog.value = false }) {
            Card(shape = RoundedCornerShape(8.dp)) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "시간을 선택해주세요")
                    Spacer(modifier = Modifier.height(12.dp))
                    TimePicker(
                        state = timePickerState,
                        layoutType = TimePickerLayoutType.Vertical
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        TextButton(onClick = { showTimeDialog.value = false }) {
                            Text(text = "취소")
                        }
                        TextButton(onClick = {
                            val time ="${timePickerState.hour}:${timePickerState.minute}"
                            hourChange(time)
                            showTimeDialog.value = false
                        }) {
                            Text(text = "확인")
                        }
                    }
                }
            }

        }
    }
    Text(
        modifier = Modifier.padding(top = 6.dp, start = 3.dp),
        text = stringResource(id = R.string.datetime)
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        OutlinedTextField(value = dateFormatter.format(date), onValueChange = {},
            enabled = false,
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .weight(3f)
                .clickable { showDateDialog.value = true })
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(value = hour, onValueChange = {},
            enabled = false,
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .weight(3f)
                .clickable { showTimeDialog.value = true })
    }

}


