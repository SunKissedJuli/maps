package com.example.maps

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform