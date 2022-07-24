package com.problem.weather.utils

import com.problem.weather.Constants
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

    fun makeIconUrl(icon: String) = Constants.OPENWEATHER_ICON_PATH + icon + "@2x.png"

    fun zoneDateTimeToString(zdt: ZonedDateTime): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a")

        return zdt.format(dateTimeFormatter)
    }

    fun isTimeStampInPast(timeStamp: Long) = ZonedDateTime.now().toEpochSecond() > timeStamp

    fun timestampToHour(timeStamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")

        return formatter.format(
            LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timeStamp), ZoneId.systemDefault()
            )
        )
    }
}