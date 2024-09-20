package com.example.maps.platform

interface MapSearchProvider {
    suspend fun searchPlaces(query: String): List<Place>
}

data class Place(val name: String, val latitude: Double, val longitude: Double)