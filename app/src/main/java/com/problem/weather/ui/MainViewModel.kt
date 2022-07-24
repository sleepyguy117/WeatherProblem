package com.problem.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.problem.weather.data.CurrentDayWeather
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

    private val _weather = MutableLiveData<CurrentDayWeather>()

    fun weatherLiveData(): LiveData<CurrentDayWeather> {
        return _weather
    }

    fun fetchCurrentDay() {
        if (lat != null && lon != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = weatherRepository.getCurrentDay(lat!!, lon!!)
                response?.let {
                    _weather.postValue(it)
                    Log.d("test", "current day weather success")
                } ?: run {
                    Log.d("test", "error current day")
                }
            }
        }
    }
}