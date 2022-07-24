package com.problem.weather.data

import com.problem.weather.network.WeatherResponse
import com.problem.weather.network.WeatherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {
//
//    private val cache = mutableMapOf<String, >()
//
//
//
//    suspend fun getCurrentDay(): String {
//
//
//    }
//
//    suspend fun getHours(): {
//
//    }

    suspend fun getWeather(query: String): WeatherResponse? {

        val response = weatherService.getWeather(query)

        return if (response.isSuccessful) response.body() else null
    }
}