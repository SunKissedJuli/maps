package com.example.maps.platform

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.core.component.KoinComponent

@Composable
expect fun KoinComponent.YandexMapPoint(
    latitude: Double,
    longitude: Double,
    pointSize: Int = 20,
    modifier: Modifier = Modifier, )

