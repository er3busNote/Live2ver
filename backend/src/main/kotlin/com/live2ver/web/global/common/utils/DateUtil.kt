package com.live2ver.web.global.common.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date

object DateUtil {
    private const val DATE_PATTERN = "yyyy-MM-dd"
    private const val DATE_FORMAT = "yyyyMMdd"
    private const val TIME_FORMAT = "HH:mm"
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm"
    private const val DATE_TIME_ISO_UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DATE_TIME_LOCAL_TIME_ZONE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    private const val DATE_TIME_FORMAT = "yyyyMMddHHmmss"
    private const val TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS"

    fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return LocalDate.now().format(formatter)
    }

    fun getCurrentDate(pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDate.now().format(formatter)
    }

    fun getCurrentDateTime(): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        return LocalDateTime.now().format(formatter)
    }

    fun getCurrentDateTime(pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.now().format(formatter)
    }

    fun getDate(year: Int, month: Int, date: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, date)
        return Date(calendar.timeInMillis)
    }

    fun toUtcString(targetDate: Date): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_ISO_UTC_PATTERN)
        val instant = targetDate.toInstant()
        val utcDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
        return utcDateTime.format(formatter)
    }

    fun toLocalDate(targetDate: Date): LocalDate {
        return Instant.ofEpochMilli(targetDate.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun toLocalString(targetDate: Date): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_LOCAL_TIME_ZONE_PATTERN)
        val localDateTime = targetDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return localDateTime.format(formatter)
    }

    fun toLocalDateTime(targetDate: Date): LocalDateTime {
        return targetDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun daysBetween(startDateStr: String, endDateStr: String, pattern: String): Long {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val startDate = LocalDate.parse(startDateStr, formatter)
        val endDate = LocalDate.parse(endDateStr, formatter)
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    fun daysBetween(endDate: LocalDate): Long {
        val startDate = LocalDate.now()
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    fun addDays(dateStr: String, days: Int, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val date = LocalDate.parse(dateStr, formatter)
        return date.plusDays(days.toLong()).format(formatter)
    }

    fun addMonths(dateStr: String, months: Int, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val date = LocalDate.parse(dateStr, formatter)
        return date.plusMonths(months.toLong()).format(formatter)
    }

    fun isToday(dateStr: String, pattern: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val date = LocalDate.parse(dateStr, formatter)
        return LocalDate.now().isEqual(date)
    }

    fun isWithinRange(dateStr: String, startDateStr: String, endDateStr: String, pattern: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val date = LocalDate.parse(dateStr, formatter)
        val startDate = LocalDate.parse(startDateStr, formatter)
        val endDate = LocalDate.parse(endDateStr, formatter)
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate))
    }

    fun isSameDate(dateStr1: String, dateStr2: String, pattern: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val date1 = LocalDate.parse(dateStr1, formatter)
        val date2 = LocalDate.parse(dateStr2, formatter)
        return date1.isEqual(date2)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("현재 날짜 - ${getCurrentDate()}")
        println("현재 날짜 - ${getCurrentDate(DATE_PATTERN)}")
        println("현재 날짜/시간 - ${getCurrentDateTime()}")
        println("현재 날짜/시간 - ${getCurrentDateTime(DATE_TIME_PATTERN)}")
        println("두 날짜 간의 차이 - ${daysBetween("2024-10-24", "2024-10-30", DATE_PATTERN)}")
        println("추가된 일수 - ${addDays("2024-10-24", 2, DATE_PATTERN)}")
        println("추가된 월수 - ${addMonths("2024-10-24", 2, DATE_PATTERN)}")
        println("오늘 날짜 여부 - ${isToday("2024-11-03", DATE_PATTERN)}")
        println("지정된 날짜 포함 여부 - ${isWithinRange("2024-11-03", "2024-11-02", "2024-11-04", DATE_PATTERN)}")
        println("두 날짜가 같은 지 여부 - ${isSameDate("2024-11-02", "2024-11-03", DATE_PATTERN)}")
    }
}