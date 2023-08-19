package com.example.fitnessapp.core.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DotSeparator(
    modifier: Modifier = Modifier
) {
    Text(text = " · ", modifier = modifier)
}