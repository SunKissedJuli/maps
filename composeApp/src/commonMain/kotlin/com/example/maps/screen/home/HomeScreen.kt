package com.example.maps.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.maps.screen.GeopositionMap.GeopositionMapScreen
import com.example.maps.screen.PointMap.PointMapScreen
import com.example.maps.strings.MapsResourceStrings
import com.example.maps.theme.MapsTheme

class HomeScreen: Screen{
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        Scaffold {
            Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){

                Button(onClick = {navigator.push(PointMapScreen())}){
                    Text(text = MapsResourceStrings.point_map)
                }

                Spacer(Modifier.height(20.dp))

                Button(onClick = {navigator.push(GeopositionMapScreen())}){
                    Text(text = MapsResourceStrings.my_geoposition)
                }

            }
        }
    }
}