package com.example.maps.platform

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.maps.images.MapsResourceImages
import com.yandex.mapkit.Animation
import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import java.util.Locale

@SuppressLint("ServiceCast")
@Composable
actual fun YandexMapPoint(latitude: Double, longitude: Double, pointSize: Int, modifier: Modifier) {
    val mapView = CustomMapView(LocalContext.current)
    MapKitFactory.getInstance().onStart()
    mapView.onStart()
 //   var geoObject: GeoObject? by remember { mutableStateOf(null) }
    val geoObjectTapListener = object : GeoObjectTapListener {
        override fun onObjectTap(event: GeoObjectTapEvent): Boolean {
           // geoObject = event.geoObject
            val geoObject = event.geoObject
            showObjectInfo(geoObject)
            return true
        }
    }
    mapView.map.addTapListener(geoObjectTapListener)
    AddPlacemark(mapView.map, LocalContext.current, latitude, longitude, pointSize)
    mapView.map.move(CameraPosition(Point(latitude, longitude),
        16.0f, 0.0f, 0.0f),
        Animation(Animation.Type.SMOOTH, 0f), null)
    //key(geoObject){
      //  ShowObjectInfo(geoObject)
   // }
    AndroidView(modifier = modifier.fillMaxWidth(), factory = { mapView })

}

@Composable
fun AddPlacemark(map: Map, context: Context, latitude: Double, longitude: Double, size: Int = 20) {
    val point = Point(latitude, longitude)
    val bitmap = BitmapFactory.decodeResource(context.resources, MapsResourceImages.dog)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false)

    map.mapObjects.addPlacemark(
        point,
        ImageProvider.fromBitmap(resizedBitmap)
    )
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

private fun showObjectInfo(geoObject: GeoObject) {
    if(geoObject!=null){
        val name = geoObject.name
        Log.d("sdggd", "хуй имя " + name)
      //  Column(Modifier.fillMaxWidth().height(50.dp).background(MaterialTheme.colorScheme.background)) {
         //   Text(text = "Название: " + name) }
    }
}



@Composable
private fun ShowInfoPanel(geoObject: GeoObject){

}