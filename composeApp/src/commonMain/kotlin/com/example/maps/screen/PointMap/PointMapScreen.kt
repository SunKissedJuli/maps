package com.example.maps.screen.PointMap

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.example.maps.component.HorizontalLine
import com.example.maps.data.MapPoint
import com.example.maps.images.MapsResourceImages
import com.example.maps.platform.YandexMapPoint
import com.example.maps.strings.MapsResourceStrings
import io.github.skeptick.libres.compose.painterResource
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class PointMapScreen: Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { PointMapViewModel() }
        val uiState: PointMapState by viewModel.stateFlow.collectAsState()
        var visible by remember { mutableStateOf(false) }
        var isRotated by remember { mutableStateOf(false) }
        val rotation by animateFloatAsState(
            targetValue = if (isRotated) 180f else 0f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        )
        val offsetYPanel by animateDpAsState(
            targetValue = if (visible) 0.dp else 100.dp,
            animationSpec = tween(durationMillis = 300))

        LaunchedEffect(viewModel){
            launch {
               viewModel.mapObjectHandler.observePoints { newPoint ->
                    viewModel.updatePoint(newPoint)
                   visible = true
                   isRotated = !isRotated
                }
            }
        }

        Scaffold {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){

                Column(
                    Modifier.fillMaxWidth().fillMaxSize().align(Alignment.BottomEnd).background(
                        MaterialTheme.colorScheme.primary)) {
                    YandexMapPoint(
                        latitude = 55.75172681525427,
                        longitude = 37.61802944543841,
                        pointSize = 140,
                        modifier = Modifier.clip(RoundedCornerShape(topEnd = 35.dp, topStart = 35.dp)))
                }

                if(uiState.mapPoint!= MapPoint.empty){

                    Icon(painterResource(MapsResourceImages.icon_map_locator),
                        contentDescription = "", tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.offset { IntOffset(uiState.mapPoint.offset.x-60, uiState.mapPoint.offset.y-180)}
                            .graphicsLayer { rotationY = rotation }.clickable{isRotated = !isRotated}
                    )

                    Box(Modifier.fillMaxWidth()
                        .offset (y = offsetYPanel)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topEnd = 35.dp, topStart = 35.dp))
                        .background(MaterialTheme.colorScheme.background)){

                        Column(Modifier.fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                            Row(Modifier.fillMaxWidth().padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painterResource(MapsResourceImages.dog), contentDescription = "",
                                    modifier = Modifier.size(40.dp).clip(CircleShape))
                                Spacer(Modifier.width(20.dp))
                                Text(text = MapsResourceStrings.taxi,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp
                                )
                            }
                            HorizontalLine()

                            Text(text = uiState.mapPoint.name,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp)
                            Text(text = MapsResourceStrings.latitude + " ${uiState.mapPoint.latitude}", Modifier.padding(top = 10.dp, bottom = 5.dp))
                            Text(text = MapsResourceStrings.longitude + " ${uiState.mapPoint.longitude}")
                        }
                    }
                }
            }
        }
    }
}

