package com.problem.weather.network

import com.problem.weather.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("appid") appId: String = Constants.OPENWEATHER_KEY
    ): Response<WeatherResponse>

    //  for some reason the 3.0 onecall gives a 401 error.
    @GET("/data/2.5/onecall")
    suspend fun getOneCall(
        @Query("lat") lat: Double = 34.0522,
        @Query("lon") lon: Double = 118.2437,
        @Query("exclude") exclude: String = "minutely",
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = Constants.OPENWEATHER_KEY
    ): Response<OneCallResponse>
}