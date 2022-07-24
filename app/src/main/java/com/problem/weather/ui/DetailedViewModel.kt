package com.problem.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.problem.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    var lat: Double? = null
    var lon: Double? = null

    fun fetchHours() {
        if (lat != null && lon != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = weatherRepository.getHours(lat!!, lon!!)
                response?.let {

                    Log.d("test", "current day hours success")
                } ?: run {
                    Log.d("test", "error current day hours")
                }
            }
        }
    }
}