package com.example.maps.screen.GeopositionMap

import com.example.maps.platform.BaseScreenModel
import com.example.maps.utils.MapObjectHandler
import org.koin.core.component.inject

internal class GeopositionMapViewModel: BaseScreenModel<Unit, Unit>(Unit){

    val mapObjectHandler: MapObjectHandler by inject()

}