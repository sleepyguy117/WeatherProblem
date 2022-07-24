package com.problem.weather.ui

import androidx.lifecycle.ViewModel
import com.problem.weather.Test
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var test: Test
}