package com.problem.weather.network

import com.squareup.moshi.Json

data class WeatherResponse (
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
) {
    data class Clouds (
        val all: Long
    )

    data class Coord (
        val lon: Double,
        val lat: Double
    )

    data class Main (
        val temp: Double,

        @Json(name = "feels_like")
        val feelsLike: Double,

        @Json(name = "temp_min")
        val tempMin: Double,

        @Json(name = "temp_max")
        val tempMax: Double,

        val pressure: Long,
        val humidity: Long,

        @Json(name = "sea_level")
        val seaLevel: Long,

        @Json(name = "grnd_level")
        val grndLevel: Long
    )

    data class Sys (
        val country: String,
        val sunrise: Long,
        val sunset: Long
    )

    data class Weather (
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Wind (
        val speed: Double,
        val deg: Long,
        val gust: Double
    )
}

