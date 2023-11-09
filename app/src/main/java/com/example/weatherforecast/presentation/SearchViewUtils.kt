package com.example.weatherforecast.presentation

object SearchViewUtils {
    @JvmStatic
    fun resultsString(results: List<String>?): String? {
        return results?.joinToString()
    }
}