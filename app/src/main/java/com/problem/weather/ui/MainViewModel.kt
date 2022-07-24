package com.problem.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.problem.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {


    fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.getWeather("Los Angeles, United States")
            response?.let {
                Timber.d("temp = ${it.main.temp}")
            } ?: run {
                Timber.d("error fetching weather")
            }
        }
    }
}