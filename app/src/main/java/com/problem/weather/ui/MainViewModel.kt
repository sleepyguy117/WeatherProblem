package com.problem.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.problem.weather.Test
import com.problem.weather.network.WeatherService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


    @Inject
    lateinit var weatherService: WeatherService

    @Inject lateinit var test: Test

    fun fetchWeather() {


        viewModelScope.launch(Dispatchers.IO) {

            val response = weatherService.getWeather("Los Angeles, Unites States")

            if (response.isSuccessful) {

                Log.d("test", response.body() ?: "no body")

            }
            else {

                Log.d("test", response.body() ?: "error")
            }
        }
    }
}