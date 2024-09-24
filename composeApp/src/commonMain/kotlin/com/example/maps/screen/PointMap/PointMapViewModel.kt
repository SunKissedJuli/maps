package com.example.maps.screen.PointMap

import com.example.maps.data.MapPoint
import com.example.maps.platform.BaseScreenModel
import com.example.maps.utils.MapObjectHandler
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce


internal class PointMapViewModel: BaseScreenModel<PointMapState, Unit>(PointMapState.InitState){

    val mapObjectHandler: MapObjectHandler by inject()

    fun updatePoint(newPoint: MapPoint) = blockingIntent{
        reduce { state.copy(mapPoint = newPoint) }
    }

}