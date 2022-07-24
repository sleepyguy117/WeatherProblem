package com.problem.weather.data

data class WeatherHours(
    var timestamp: Long,
    var temp: Double,
    var icon: String,
    var description: String
)
