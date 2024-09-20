package com.example.maps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.maps.screen.root.Root
import com.yandex.mapkit.MapKitFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MapKitFactory.setApiKey("1b2fb402-13cf-4186-a4d0-f748f52f3c81")
            MapKitFactory.setLocale("ru_RU")
            Root()
        }
    }
}
