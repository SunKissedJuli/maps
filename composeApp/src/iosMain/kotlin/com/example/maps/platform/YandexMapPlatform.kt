package com.example.maps.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.core.component.KoinComponent

@Composable
actual fun KoinComponent.YandexMapPoint(
    latitude: Double,
    longitude: Double,
    pointSize: Int,
    modifier: Modifier){}