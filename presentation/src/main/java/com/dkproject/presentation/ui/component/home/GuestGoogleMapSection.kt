package com.dkproject.presentation.ui.component.home

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun GuestGoogleMapSection(
    lat: Double,
    lng: Double,
    cameraPositionState: CameraPositionState
) {
    Log.e("GuestGoogleMapSection", "$lat, $lng")
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        LatLng(
            lat,
           lng
        ), 13f)
    val properties by remember {
        mutableStateOf(
            MapProperties(
                isTrafficEnabled = true,
                mapType = MapType.NORMAL
            )
        )
    }
    Card(modifier= Modifier
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

private suspend fun CameraPositionState.MoveLocation(location: com.google.android.gms.maps.model.LatLng) {
    this.animate(
        update = CameraUpdateFactory.newLatLngZoom(
            location, 13f
        ),
        durationMs = 1500
    )
}