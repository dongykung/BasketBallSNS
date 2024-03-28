package com.dkproject.data.usecase.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dkproject.domain.model.MyLocation
import com.dkproject.domain.usecase.location.GetLastLocationUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetLastLocationUseCaseImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val locationClient: FusedLocationProviderClient
) : GetLastLocationUseCase {
    override fun requestCurrentLocation(): Flow<MyLocation> = flow {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@flow
        }
        val data = MyLocation(37.555134, 126.936893)
         locationClient.lastLocation.addOnSuccessListener {
            data.Lat=it.latitude
            data.Lng=it.longitude
        }.addOnFailureListener {
             Log.d("GetLastLocationUseCaseImpl", it.toString())
        }.await()
        emit(data)
    }

}


//fun Context.hasLocationPermission(): Boolean {
//    return ContextCompat.checkSelfPermission(
//        this,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//        this,
//        Manifest.permission.ACCESS_COARSE_LOCATION
//    ) == PackageManager.PERMISSION_GRANTED
//}
