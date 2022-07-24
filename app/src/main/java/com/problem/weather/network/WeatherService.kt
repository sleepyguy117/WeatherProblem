package com.problem.weather.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("q") query: String, @Query("APPID") appId: String = "a12a5b410137e7a464cecd889521d3b2"): Response<String>
}