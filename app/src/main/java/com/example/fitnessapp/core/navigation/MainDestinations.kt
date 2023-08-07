package com.example.fitnessapp.core.navigation

sealed class MainDestinations(val route: String) {
    object Overview : MainDestinations(route = "overview")
    object Nutrition : MainDestinations(route = "nutrition")
}