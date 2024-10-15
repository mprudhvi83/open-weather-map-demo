package com.open.weather.map
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import java.util.Locale


object Utils {
    private val BASIC_PERMISSION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val BASIC_PERMISSION_REQUESTCODE = 0

    fun hasAccessFinePermission(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestFineLocationPermission(activity: Activity?) {
        ActivityCompat.requestPermissions(activity!!, BASIC_PERMISSION, BASIC_PERMISSION_REQUESTCODE)
    }
    fun getCityName(context: Activity, completion: (String) -> Unit) {
        var cityName: String? = null
        var fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestFineLocationPermission(context)
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnCompleteListener(context, OnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val location = task.result

                // Handle the location object here
                val latitude = location.latitude
                val longitude = location.longitude
                val geoCoder = Geocoder(context, Locale.getDefault())
                val address = geoCoder.getFromLocation(latitude, longitude, 1)
                cityName = address?.get(0)?.locality
                if (cityName == null) {
                    cityName = address?.get(0)?.adminArea
                    if (cityName == null) {
                        cityName = address?.get(0)?.subAdminArea
                    }
                }
                println("cityName = $cityName")


            } else {
                // Handle the error
                // ...
            }
            cityName?.let {
                completion(it)
            }
        })
    }
}

//private fun <TResult> Task<TResult>.addOnCompleteListener(onCompleteListener: OnCompleteListener<Any?>) {
//
//}
