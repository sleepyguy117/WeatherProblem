package com.problem.weather.network

import com.squareup.moshi.Json

data class OneCallResponse (
    val lat: Double,
    val lon: Double,
    val timezone: String,

    @Json(name = "timezone_offset")
    val timezoneOffset: Long,

    val current: Current,
    val minutely: List<Minutely>,
    val hourly: List<Current>,
    val daily: List<Daily>
) {

    data class Current (
        val dt: Long,
        val sunrise: Long? = null,
        val sunset: Long? = null,
        val temp: Double,

        @Json(name = "feels_like")
        val feelsLike: Double,

        val pressure: Long,
        val humidity: Long,

        @Json(name = "dew_point")
        val dewPoint: Double,

        val uvi: Double,
        val clouds: Long,
        val visibility: Long,

        @Json(name = "wind_speed")
        val windSpeed: Double,

        @Json(name = "wind_deg")
        val windDeg: Long,

        val weather: List<Weather>,

        @Json(name = "wind_gust")
        val windGust: Double? = null,

        val pop: Long? = null
    )

    data class Weather (
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Daily (
        val dt: Long,
        val sunrise: Long,
        val sunset: Long,
        val moonrise: Long,
        val moonset: Long,

        @Json(name = "moon_phase")
        val moonPhase: Double,

        val temp: Temp,

        @Json(name = "feels_like")
        val feelsLike: FeelsLike,

        val pressure: Long,
        val humidity: Long,

        @Json(name = "dew_point")
        val dewPoint: Double,

        @Json(name = "wind_speed")
        val windSpeed: Double,

        @Json(name = "wind_deg")
        val windDeg: Long,

        @Json(name = "wind_gust")
        val windGust: Double,

        val weather: List<Weather>,
        val clouds: Long,
        val pop: Double,
        val uvi: Double,
        val rain: Double? = null
    )

    data class FeelsLike (
        val day: Double,
        val night: Double,
        val eve: Double,
        val morn: Double
    )

    data class Temp (
        val day: Double,
        val min: Double,
        val max: Double,
        val night: Double,
        val eve: Double,
        val morn: Double
    )

    data class Minutely (
        val dt: Long,
        val precipitation: Long
    )
}
