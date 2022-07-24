package com.problem.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.problem.weather.data.Lce
import com.problem.weather.data.WeatherHours
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

    private val _weatherHours = MutableLiveData<Lce<List<WeatherHours>>>()

    fun weatherHoursLiveData(): LiveData<Lce<List<WeatherHours>>> {
        return _weatherHours
    }

    fun fetchHours() {
        if (lat != null && lon != null) {
            _weatherHours.value = Lce.Loading()
            viewModelScope.launch(Dispatchers.IO) {
                val response = weatherRepository.getHours(lat!!, lon!!)
                response?.let {
                    Log.d("test", "current day hours success")
                    _weatherHours.postValue(Lce.Content(it))
                } ?: run {
                    _weatherHours.postValue(Lce.Error())
                    Log.d("test", "error current day hours")
                }
            }
        }
    }
}