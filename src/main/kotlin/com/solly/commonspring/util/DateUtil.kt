/**
 * 문자열 <-> 날짜(LocalDate, LocalDateTime) 변환 Util
 * 자바 호환 : DateUtil
 */
@file:JvmName("DateUtil")
package com.solly.commonspring.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.let

fun LocalDateTime?.stringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String? {

    if(this == null) {
        return null
    }

    return try {
        val formatter = DateTimeFormatter.ofPattern(format)

        formatter.format(this)
    } catch (e: IllegalArgumentException) {
        println("IllegalArgumentException format: '$format'")
        null
    }
}

fun LocalDate?.stringFormat(format: String = "yyyy-MM-dd", startDateAt: Boolean = true): String? {

    if(this == null) {
        return null
    }

    return try {
        val formatter = DateTimeFormatter.ofPattern(format)

        val date = if(startDateAt) {
            /* 시작일 */
            this.atStartOfDay()
        } else {
            /* 종료일 */
            atTime(23, 59, 59)
        }

        formatter.format(date)
    } catch (e: IllegalArgumentException) {
        println("IllegalArgumentException format: '$format'")
        null
    }

}

fun String?.toLocalDateTime(format: String = "yyyyMMddHHmmss"): LocalDateTime? {

    return this?.let {
        try {
            LocalDateTime.parse(this, DateTimeFormatter.ofPattern(format))
        } catch (e: DateTimeParseException) {
            println("Invalid date format: '$this' with pattern '$format'")
            null
        } catch (e: IllegalArgumentException) {
            println("IllegalArgumentException format: '$format'")
            null
        }
    }
}

fun String?.toLocalDate(format: String = "yyyyMMdd"): LocalDate? {

    return this?.let {
        try {
            LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
        } catch (e: DateTimeParseException) {
            println("Invalid date format: '$this' with pattern '$format'")
            null
        } catch (e: IllegalArgumentException) {
            println("IllegalArgumentException format: '$format'")
            null
        }
    }
}