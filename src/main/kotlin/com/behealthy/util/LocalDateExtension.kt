package com.behealthy.util

import java.time.LocalDate

object LocalDateExtension {

    fun LocalDate.withStartDayOfMonth() = withDayOfMonth(1)
    fun LocalDate.withEndDayOfMonth() = plusMonths(1).withStartDayOfMonth().minusDays(1)
}