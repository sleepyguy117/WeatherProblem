package com.problem.weather.data

import com.problem.weather.network.OneCallResponse
import com.problem.weather.network.WeatherResponse
import com.problem.weather.network.WeatherService
import com.problem.weather.utils.Utils
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {

    private val oneCallResponseCache = mutableMapOf<String, OneCallResponse>()

    private fun makeKey(lat: Double, lon: Double): String {
        return "$lat,$lon"
    }

    suspend fun getCurrentDay(lat: Double, lon: Double): CurrentDayWeather? {

        val key = makeKey(lat, lon)

        val cachedResponse = oneCallResponseCache[key]

        // check cache, if its too old don't use it
        var data: OneCallResponse? = cachedResponse?.run {
            if (current.dt + MAX_RESPONSE_AGE_SEC < ZonedDateTime.now().toEpochSecond()) {
                cachedResponse
            } else null
        }

        if (data == null) {
            // fetch data, fill cache if body
            val newResponse = weatherService.getOneCall(lat, lon)

            if (newResponse.isSuccessful) {
                newResponse.body()?.let {
                    oneCallResponseCache[key] = it
                    data = it
                }
            }
        }

        return data?.let { theData ->

            // find the day
            val day = theData.daily.find { day ->
                Utils.isToday(day.dt)
            }

            day?.let {
              CurrentDayWeather(it.temp.max, it.temp.min, it.weather[0].icon, it.weather[0].description)
            }
        }
    }

    suspend fun getHours(lat: Double, lon: Double): List<OneCallResponse.Weather>? {

        val key = makeKey(lat, lon)

        val cachedResponse = oneCallResponseCache[key]

        // check cache, if its too old don't use it
        var data: OneCallResponse? = cachedResponse?.run {
            if (current.dt + MAX_RESPONSE_AGE_SEC < ZonedDateTime.now().toEpochSecond()) {
                cachedResponse
            } else null
        }

        if (data == null) {
            // fetch data, fill cache if body
            val newResponse = weatherService.getOneCall(lat, lon)

            if (newResponse.isSuccessful) {
                newResponse.body()?.let {
                    oneCallResponseCache[key] = it
                    data = it
                }
            }
        }

        return data?.let { theData ->
            theData.hourly.map {  hour ->
                hour.weather[0]
            }
        }
    }

    suspend fun getWeather(query: String): WeatherResponse? {

        val response = weatherService.getWeather(query)

        return if (response.isSuccessful) response.body() else null
    }

    companion object {
        const val MAX_RESPONSE_AGE_SEC = 60
    }
}