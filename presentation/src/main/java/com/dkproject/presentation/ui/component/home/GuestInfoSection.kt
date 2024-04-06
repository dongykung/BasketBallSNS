package com.dkproject.presentation.ui.component.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dkproject.domain.model.home.Guest
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import com.dkproject.presentation.util.getDayOfWeekFromMillis
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GuestInfoSection(
    modifier: Modifier = Modifier,
    guest: Guest,
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                guest.lat,
                guest.lng
            ), 13f
        )
    }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                isTrafficEnabled = true,
                mapType = MapType.NORMAL
            )
        )
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val timeformatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(2f),
                text = "일시",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                modifier = Modifier.weight(4f),
                text = formatter.format(guest.daydate) + " (${getDayOfWeekFromMillis(guest.detaildate)})"
                        + " ${timeformatter.format(guest.detaildate)}",
                style = TextStyle(color = Color.Black, fontSize = 18.sp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(2f),
                text = "모집인원",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                modifier = Modifier.weight(4f),
                text = stringResource(id = R.string.guestsize, guest.guestsUid.size, guest.count)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(2f),
                text = "모집 포지션",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            FlowRow(
                modifier = Modifier.weight(4f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                guest.positionList.forEach {
                    Text(text = it)
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Box(modifier=Modifier.fillMaxWidth()) {
            Text(text = guest.content,
                style = TextStyle(color=Color.Black, fontSize = 18.sp),
                letterSpacing = 1.sp)
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "위치 정보",
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = guest.detailAddress,
                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(modifier=Modifier.clickable {
                val clipManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label",guest.detailAddress)
                clipManager.setPrimaryClip(clip)
            },
                border = BorderStroke(1.dp,Color.Gray),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(modifier=Modifier.padding(vertical = 3.dp, horizontal = 12.dp),
                    text = "복사")
            }

        }

        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier=Modifier.fillMaxWidth()
            .pointerInput(Unit){
                detectDragGestures { change, dragAmount ->
                    cameraPositionState.move(CameraUpdateFactory.scrollBy(
                        dragAmount.x,dragAmount.y
                    ))
                }
            }
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                properties = properties,
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            guest.lat,
                           guest.lng
                        )
                    ),
                    title = "location"
                )

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun GuestInfoPreview() {
    BasketballSNSTheme {
        GuestInfoSection(
            guest = Guest(
                "", "", "", emptyList(),
                0, "", 0.0, 0.0, emptyList(), "", 0, 0
            )
        )
    }
}