package com.problem.weather.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object Utils {

    fun isToday(timeStamp: Long): Boolean {

        val now = ZonedDateTime.now().withHour(0).withMinute(0)
            .withSecond(0).withNano(0)


        val dayOfStamp =
            ZonedDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), ZoneId.systemDefault())
                .withHour(0).withMinute(0)
                .withSecond(0).withNano(0)


        return now == dayOfStamp
    }
}