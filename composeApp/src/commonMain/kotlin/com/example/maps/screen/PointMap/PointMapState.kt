package com.example.maps.screen.PointMap

import androidx.compose.ui.unit.IntOffset
import com.example.maps.data.MapPoint

data class PointMapState(
    val mapPoint: MapPoint,
){
    companion object{
        val InitState = PointMapState(
            mapPoint = MapPoint.empty,
        )
    }
}
