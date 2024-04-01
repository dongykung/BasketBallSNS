package com.dkproject.presentation.ui.screen.home.shop

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.domain.model.MyLocation
import com.dkproject.presentation.ui.component.HomeTopAppBar
import com.dkproject.presentation.ui.component.showToastMessage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.type.LatLng
import java.io.IOException
import java.util.Locale
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetAddressScreen(
    viewModel: GoogleMapCoordinateViewModel = hiltViewModel(),
    updatelat: (Double) -> Unit,
    updatelng: (Double) -> Unit,
    updateaddress: (String) -> Unit,
    finish: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val searchState = viewModel.searchstate

    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(
            com.google.android.gms.maps.model.LatLng(
                state.latitude,
                state.longitude
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


    LaunchedEffect(state) {
        Log.d("laucntest", state.toString())
        val data = com.google.android.gms.maps.model.LatLng(state.latitude, state.longitude)
        cameraPositionState.OnLocation(data)
    }
//    LaunchedEffect(key1 = cameraPositionState.isMoving) {
//        Log.d("mapevent", cameraPositionState.position.toString())
//    }
    Scaffold(topBar = {
        HomeTopAppBar(title = "",onBack = true,
            onBackClick = finish)
    }) {innerPadding->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                Marker(
                    state = MarkerState(
                        position = com.google.android.gms.maps.model.LatLng(
                            cameraPositionState.position.target.latitude,
                            cameraPositionState.position.target.longitude
                        )
                    ),
                    title = "myLocation"
                )

            }
            SearchSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                visible = searchState.isNotEmpty(),
                context = context,
                itemList = searchState.toList(),
                viewModel = viewModel,
                ontextChange = { query ->
                    viewModel.searchPlaces(query)
                }
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(bottom = 12.dp)
                .align(Alignment.BottomCenter),
                onClick = {
                    getAddress(
                        context,
                        cameraPositionState.position.target.latitude,
                        cameraPositionState.position.target.longitude,
                        updatelat = updatelat,
                        updatelng=updatelng,
                        updateaddress=updateaddress,
                        finish = finish
                    )
                }) {
                Text(text = "위치 지정하기")
            }
        }
    }
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    visible: Boolean,
    context: Context,
    itemList: List<AutocompleteResult>,
    viewModel: GoogleMapCoordinateViewModel,
    ontextChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.background(Color.White, RoundedCornerShape(8.dp))
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text, onValueChange = {
                text = it
                ontextChange(it)
            },
            colors = TextFieldDefaults.colors(Color.White)
        )
        AnimatedVisibility(visible = visible) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(itemList) {
                    SearchResultItem(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            getLocation(context, viewModel, it.address)
                            text = ""
                            ontextChange("")
                        }, it.address
                    )
                }
            }
        }
    }
}


@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    address: String
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = address,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun getAddress(context: Context, lat: Double, lng: Double,
                       updatelat:(Double)->Unit,
                       updatelng:(Double)->Unit,
                       updateaddress:(String)->Unit,
                      finish:()->Unit) {
    val geocoder = Geocoder(context, Locale.KOREA)

    if (Build.VERSION.SDK_INT < 33) {
        val address = geocoder.getFromLocation(lat, lng, 1)?.let {
//            sharedViewModel.updatelatitude(it[0].latitude)
//            sharedViewModel.updatelongitude(it[0].longitude)
            updatelat(it[0].latitude)
            updatelng(it[0].longitude)
            var value = it[0].getAddressLine(0)
            if(value.contains("대한민국"))
                value = value.replace("대한민국","").trim()
//            sharedViewModel.updateDetailAddress(value)
            updateaddress(value)
        }
        finish()
    } else {
        val geocoderListener = @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<Address>) {
                var value = addresses[0].getAddressLine(0)
                if(value.contains("대한민국"))
                    value = value.replace("대한민국", "").trim()
//                sharedViewModel.updatelatitude(addresses[0].latitude)
//                sharedViewModel.updatelongitude(addresses[0].longitude)
                updatelat(addresses[0].latitude)
                updatelng(addresses[0].longitude)
                updateaddress(value)
//                sharedViewModel.updateDetailAddress(value)
            }
        }
        geocoder.getFromLocation(lat, lng, 1, geocoderListener)
        finish()
    }
}

private fun getLocation(
    context: Context,
    viewModel: GoogleMapCoordinateViewModel,
    address: String
) {
    val geocoder = Geocoder(context, Locale.KOREA)
    if (Build.VERSION.SDK_INT < 33) {
        val result = geocoder.getFromLocationName(address, 1) ?: emptyList()
        val data =result[0]
        val myLocation = MyLocation(data.latitude,data.longitude)
        viewModel.updateCoordinate(myLocation)
        //todo viewModel update
    } else {
        val geocoderListener = @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<Address>) {
                val result = addresses[0]
                Log.d("myaddress", result.toString())
                val data = MyLocation(result.latitude, result.longitude)
                viewModel.updateCoordinate(data)
            }
        }

        geocoder.getFromLocationName(address, 1, geocoderListener)
    }
}

private suspend fun CameraPositionState.OnLocation(location: com.google.android.gms.maps.model.LatLng) {
    this.animate(
        update = CameraUpdateFactory.newLatLngZoom(
            location, 13f
        ),
        durationMs = 1500
    )
}
