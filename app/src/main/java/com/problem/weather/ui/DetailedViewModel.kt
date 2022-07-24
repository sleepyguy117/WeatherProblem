package com.problem.weather.ui

import androidx.lifecycle.ViewModel
import com.problem.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

}