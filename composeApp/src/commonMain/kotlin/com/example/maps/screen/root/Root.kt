package com.example.maps.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.maps.screen.home.HomeScreen
import com.example.maps.theme.MapsTheme

@Composable
fun Root(){
    MapsTheme {
        Navigator(HomeScreen()) {
            CompositionLocalProvider(RootNavigator provides it,) {
                CurrentScreen()
            }
        }
    }
}