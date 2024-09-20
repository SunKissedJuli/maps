package com.example.maps.screen.PointMap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.example.maps.platform.YandexMapPoint
import com.example.maps.screen.GeopositionMap.GeopositionMapViewModel

class PointMapScreen: Screen {
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { PointMapViewModel() }
        Scaffold {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){

                Column(
                    Modifier.fillMaxWidth().height(600.dp).align(Alignment.BottomEnd).background(
                        MaterialTheme.colorScheme.primary)) {
                    YandexMapPoint(
                        latitude = 55.75172681525427,
                        longitude = 37.61802944543841,
                        pointSize = 140,
                        modifier = Modifier.clip(RoundedCornerShape(topEnd = 35.dp, topStart = 35.dp)))

                }

            }
        }
    }
}

