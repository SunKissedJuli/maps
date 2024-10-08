package com.example.maps.platform

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.maps.images.MapsResourceImages
import com.example.maps.utils.MapObjectHandler
import com.yandex.mapkit.Animation
import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@SuppressLint("ServiceCast")
@Composable
actual fun KoinComponent.YandexMapPoint(latitude: Double, longitude: Double, pointSize: Int, modifier: Modifier) {
    val mapObjectHandler: MapObjectHandler by inject()
    val mapView = CustomMapView(LocalContext.current)
    var panelPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var mapPoint: MapPoint by remember {
        mutableStateOf(MapPoint.empty)
    }

    LaunchedEffect(Unit) {
        MapKitFactory.getInstance().onStart()
    }

    mapView.onStart()

    val cameraListener = CameraListener { p0, p1, p2, p3 ->
        if(mapPoint != MapPoint.empty){
            mapPoint = MapPoint.empty
            val newPoint = mapPoint
            mapObjectHandler.addPoint(newPoint)
        }
    }

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
            mapPoint = point
            Log.d("ss", "point = " + point)
            mapObjectHandler.addPoint(point)
        }
        true
    }

    mapView.map.addCameraListener(cameraListener)
    mapView.map.addTapListener(geoObjectTapListener)
    AddPlacemark(mapView.map, LocalContext.current, latitude, longitude, pointSize)
    mapView.map.move(CameraPosition(Point(latitude, longitude),
        16.0f, 0.0f, 0.0f),
        Animation(Animation.Type.SMOOTH, 0f), null)

    AndroidView(modifier = modifier.fillMaxWidth(), factory = { mapView })
}

@Composable
fun AddPlacemark(map: Map, context: Context, latitude: Double, longitude: Double, size: Int = 20) {
    val point = Point(latitude, longitude)
    val bitmap = BitmapFactory.decodeResource(context.resources, MapsResourceImages.dog)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false)
    map.mapObjects.addPlacemark(point, ImageProvider.fromBitmap(resizedBitmap))
}

class CustomMapView(context: Context) : MapView(context) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_UP -> parent.requestDisallowInterceptTouchEvent(false)
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(ev)
    }
}