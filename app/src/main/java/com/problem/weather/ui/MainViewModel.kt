package com.problem.weather.ui

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.problem.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    var lat: Double? = null
    var lon: Double? = null

    fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.getWeather("Los Angeles, United States")
            response?.let {
                Log.d("test", "temp = ${it.main.temp}")
            } ?: run {
                Log.d("test", "error weather")
            }
        }
    }

    fun fetchCurrentDay() {
        if (lat != null && lon != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = weatherRepository.getCurrentDay(lat!!, lon!!)
                response?.let {

                    Log.d("test", "current day weather success")
                } ?: run {
                    Log.d("test", "error current day")
                }
            }
        }
    }
}