package com.example.fitnessapp.core.util

import java.util.Locale

private fun String.toTitleCaseFirstCharIfItIsLowercase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.capitalizeEachWord(delimiters: String = " "): String {
    return split(delimiters).joinToString(delimiters) { word ->
        val smallCaseWord = word.lowercase()
        smallCaseWord.toTitleCaseFirstCharIfItIsLowercase()
    }
}
