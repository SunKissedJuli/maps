package com.example.maps.platform

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import com.example.maps.data.MapPoint
import com.example.maps.utils.MapObjectHandler
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.yandex.mapkit.location.LocationListener

@Composable
actual fun KoinComponent.YandexMapGeolocation(
    pointSize: Int,
    modifier: Modifier
){
    val mapObjectHandler: MapObjectHandler by inject()
    val mapView = CustomMapView(LocalContext.current)
    var panelPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var userLocation by remember { mutableStateOf<Point?>(null) }

    LaunchedEffect(Unit) {
        MapKitFactory.getInstance().onStart()
        requestUserLocation { location ->
            userLocation = location
            if (location != null) {
                mapView.map.move(
                    CameraPosition(location, 16.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
                panelPosition = Offset(location.longitude.toFloat(), location.latitude.toFloat())
            }
        }
    }

    mapView.onStart()

    val geoObjectTapListener = GeoObjectTapListener { event ->
        val geoLatitude = event.geoObject.geometry.firstOrNull()?.point?.latitude
        val geoLongitude = event.geoObject.geometry.firstOrNull()?.point?.longitude
        val name = event.geoObject.name

        if (geoLatitude != null && geoLongitude != null && name != null) {
            val screenPoint = mapView.mapWindow.worldToScreen(Point(geoLatitude, geoLongitude))
            if (screenPoint != null) {
                panelPosition = Offset(screenPoint.x, screenPoint.y)
                Log.d("ss", "screenPoint x = ${screenPoint.x}  y = ${screenPoint.y}")
            }
            val point = MapPoint(latitude = geoLatitude, longitude = geoLongitude, name = name, IntOffset(panelPosition.x.toInt(), panelPosition.y.toInt()))
            Log.d("ss", "panelPosition = $panelPosition")
            Log.d("ss", "point = $point")
            mapObjectHandler.addPoint(point)
        }
        true
    }

    mapView.map.addTapListener(geoObjectTapListener)
    AddPlacemark(mapView.map, LocalContext.current,
        userLocation?.latitude ?: 55.75172681525427, userLocation?.longitude ?: 37.61802944543841)

    AndroidView(modifier = modifier.fillMaxWidth(), factory = { mapView })
}

private fun requestUserLocation(onLocationReceived: (Point?) -> Unit) {
    val mapKitInstance = MapKitFactory.getInstance()
    val locationManager = mapKitInstance.createLocationManager()

    locationManager.requestSingleUpdate(object : LocationListener {
        override fun onLocationUpdated(location: Location) {

            onLocationReceived(location.position)
        }

        override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
            when (locationStatus) {
                LocationStatus.NOT_AVAILABLE -> {
                    Log.d("sdg", "Location NOT_AVAILABLE")
                    onLocationReceived(null) // Notify that location is not available
                }
                LocationStatus.AVAILABLE -> {
                    Log.d("sdg", "Location AVAILABLE")
                }

                LocationStatus.RESET -> {
                    Log.d("sd", "Location RESET")
                }
            }
        }

    })
}