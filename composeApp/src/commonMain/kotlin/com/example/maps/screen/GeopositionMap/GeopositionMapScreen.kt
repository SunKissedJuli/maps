package com.example.maps.screen.GeopositionMap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.example.maps.data.MapPoint
import com.example.maps.images.MapsResourceImages
import com.example.maps.platform.YandexMapGeolocation
import com.example.maps.platform.YandexMapPoint
import com.example.maps.screen.PointMap.PointMapViewModel
import com.example.maps.strings.MapsResourceStrings
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import io.github.skeptick.libres.compose.painterResource

class GeopositionMapScreen: Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { GeopositionMapViewModel() }
        var currentPoint: MapPoint? by remember{ mutableStateOf(null) }

        LaunchedEffect(viewModel){
            launch {
                viewModel.mapObjectHandler.observePoints { newPoint ->
                    currentPoint = newPoint
                }
            }
        }

        Scaffold {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){

                Column(
                    Modifier.fillMaxSize().align(Alignment.BottomEnd).background(
                        MaterialTheme.colorScheme.primary)) {
                    YandexMapGeolocation(
                        pointSize = 140,
                        modifier = Modifier.clip(RoundedCornerShape(topEnd = 35.dp, topStart = 35.dp)))
                }

                if(currentPoint!=null){
                    key(currentPoint){
                        Box(Modifier.fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(topEnd = 35.dp, topStart = 35.dp))
                            .background(MaterialTheme.colorScheme.background)){

                            Text(text = "${currentPoint?.latitude} ${currentPoint?.longitude}")
                        }
                    }
                }
            }
        }
    }
}