package com.example.maps.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.core.component.KoinComponent

@Composable
expect fun KoinComponent.YandexMapGeolocation(
    pointSize: Int = 20,
    modifier: Modifier = Modifier, )

