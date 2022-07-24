package com.problem.weather.data

sealed class Lce<T> {
    class Loading<T> : Lce<T>()
    data class Content<T>(val data : T) : Lce<T>()
    class Error<T> : Lce<T>()
}