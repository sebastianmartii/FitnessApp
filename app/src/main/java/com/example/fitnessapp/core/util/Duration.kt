package com.example.fitnessapp.core.util

fun duration(minutes: String, seconds: String): Double {
    return minutes.toDouble() + (seconds.toDouble()) / 100
}

fun Double.toDuration(): Double {
    val minutes = this.toInt() * 0.6
    val seconds = this % 1.0
    return (minutes + seconds)/0.6
}