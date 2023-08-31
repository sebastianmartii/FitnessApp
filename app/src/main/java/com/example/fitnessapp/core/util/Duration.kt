package com.example.fitnessapp.core.util

fun duration(minutes: String, seconds: String): Double {
    return minutes.toDouble() + (seconds.toDouble()) / 100
}