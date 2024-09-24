package com.example.maps.utils

import com.example.maps.data.MapPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MapObjectHandler {
    val objects: Channel<MapPoint>
    fun addPoint(point: MapPoint)
    fun observePoints(observer: (MapPoint) -> Unit)
}

class MapObjectHandlerImpl : MapObjectHandler {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override val objects: Channel<MapPoint> = Channel()

    override fun addPoint(point: MapPoint) {
        coroutineScope.launch {
            objects.send(point)
        }
    }

    override fun observePoints(observer: (MapPoint) -> Unit) {
        coroutineScope.launch {
            objects.receiveAsFlow().collect { point ->
                observer(point)
            }
        }
    }
}

