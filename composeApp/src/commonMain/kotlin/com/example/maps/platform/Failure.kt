package com.example.maps.platform

sealed class Failure(override val message: String) : Throwable() {
    class Message(message: String) : Failure(message)

    class Http(val code: Int, override val message: String) : Failure(message)
}