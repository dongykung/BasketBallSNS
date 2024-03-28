package com.dkproject.presentation.ui.screen.home.shop

import android.content.Context
import android.location.Address
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.MyLocation
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GoogleMapCoordinateViewModel @Inject constructor(
    private val getLastLocationUseCase: GetLastLocationUseCase,
    private val getPlacesClient: PlacesClient
) : ViewModel() {
    companion object{
        const val TAG = "GOOGLEMAPViewModel"
    }

    private val _state = MutableStateFlow(CoordinateState(37.555134, 126.936893))
    val state: StateFlow<CoordinateState> = _state.asStateFlow()

     val searchstate = mutableStateListOf<AutocompleteResult>()



   private var job :Job?=null

    init {
        getLocation()
    }

    fun updateCoordinate(coordinate: MyLocation) {
        _state.update {
            it.copy(latitude = coordinate.Lat, longitude = coordinate.Lng)
        }
    }


    fun getLocation() {
        getLastLocationUseCase.requestCurrentLocation().onEach {
            Log.e(TAG, it.toString())
            updateCoordinate(it)
        }.launchIn(viewModelScope)
    }

    fun searchPlaces(query:String){
        job?.cancel()
        searchstate.clear()
        job = viewModelScope.launch {
            delay(500L)
            val request = FindAutocompletePredictionsRequest
                .builder()
                .setQuery(query)
                .build()
            getPlacesClient
                .findAutocompletePredictions(request)
                .addOnSuccessListener {response->

                    searchstate += response.autocompletePredictions.take(5).map {
                        Log.d("auto", it.getFullText(null).toString())
                        AutocompleteResult(it.getFullText(null).toString(),it.placeId,)
                    }
                }
                .addOnFailureListener{
                    Log.e("autoerror", it.toString())
                }
        }
    }
}

data class AutocompleteResult(
    val address:String,
    val placeId:String,
)

data class CoordinateState(
    val latitude: Double,
    val longitude: Double
)
