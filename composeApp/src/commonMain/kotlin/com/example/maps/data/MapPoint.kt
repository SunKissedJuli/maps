package com.example.maps.data

import androidx.compose.ui.unit.IntOffset

data class MapPoint(
    var latitude: Double,
    var longitude: Double,
    var name: String,
    var offset: IntOffset
){
    companion object{
        val empty = MapPoint(
            latitude = 0.0,
            longitude = 0.0,
            name = "",
            offset = IntOffset(0,0))
    }
}