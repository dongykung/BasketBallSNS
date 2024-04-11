package com.dkproject.presentation.ui.component.section

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapSection(
    modifier: Modifier = Modifier,
    lat: Double,
    lng: Double,
    context: Context,
    detailAddress: String,
) {
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                lat,
                lng
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
    Column(modifier=modifier) {
        Text(
            text = "위치 정보",
            style = MaterialTheme.typography.titleLarge
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = detailAddress,
                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                modifier = Modifier.clickable {
                    val clipManager =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("label", detailAddress)
                    clipManager.setPrimaryClip(clip)
                },
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 12.dp),
                    text = "복사"
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    cameraPositionState.move(
                        CameraUpdateFactory.scrollBy(
                            dragAmount.x, dragAmount.y
                        )
                    )
                }
            }
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                properties = properties,
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            lat,
                            lng
                        )
                    ),
                    title = "location"
                )

            }
        }
    }

}