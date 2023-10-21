package util

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.Month.APRIL
import kotlinx.datetime.Month.AUGUST
import kotlinx.datetime.Month.DECEMBER
import kotlinx.datetime.Month.FEBRUARY
import kotlinx.datetime.Month.JANUARY
import kotlinx.datetime.Month.JULY
import kotlinx.datetime.Month.JUNE
import kotlinx.datetime.Month.MARCH
import kotlinx.datetime.Month.MAY
import kotlinx.datetime.Month.NOVEMBER
import kotlinx.datetime.Month.OCTOBER
import kotlinx.datetime.Month.SEPTEMBER
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class YearMonth private constructor(val year: Int, val month: Month) : Comparable<YearMonth> {
    companion object {
        fun now(): YearMonth {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return YearMonth(now.year, now.month)
        }

        fun now(clock: Clock): YearMonth {
            val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return YearMonth(now.year, now.month)
        }

        fun now(timeZone: TimeZone): YearMonth {
            val now = Clock.System.now().toLocalDateTime(timeZone)
            return YearMonth(now.year, now.month)
        }

        fun of(year: Int, month: Month): YearMonth {
            return YearMonth(year, month)
        }

        fun of(year: Int, month: Int): YearMonth? {
            return if(month in 1..12) YearMonth(year, Month.values()[month - 1]) else null
        }
    }

    fun atDay(day: Int): LocalDate {
        return LocalDate(year, month, day)
    }

    fun atEndOfMonth(): LocalDate {
        val nextMonth = month.number % 12 + 1
        return LocalDate(if (nextMonth == 1) year + 1 else year, nextMonth, 1) - DatePeriod(days = 1)
    }

    fun isAfter(other: YearMonth): Boolean {
        return compareTo(other) > 0
    }

    fun isBefore(other: YearMonth): Boolean {
        return compareTo(other) < 0
    }

    fun isLeapYear(): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    fun isValidDay(day: Int): Boolean {
        return day in 1..month.length(isLeapYear())
    }

    fun lengthOfMonth(): Int {
        return month.length(isLeapYear())
    }

    fun lengthOfYear(): Int {
        return if (isLeapYear()) 366 else 365
    }

    operator fun minus(period: DatePeriod): YearMonth {
        val newMonth = (month.number - period.months).let{if(it < 1) it + 12 else it}
        val newYear = year - period.years - if(month.number - period.months < 1) 1 else 0
        return YearMonth(newYear, Month.values()[(newMonth - 1) % 12])
    }

    operator fun plus(period: DatePeriod): YearMonth {
        val newMonth = month.number + period.months
        val newYear = year + period.years + (newMonth - 1) / 12
        return YearMonth(newYear, Month.values()[(newMonth - 1) % 12])
    }

    fun withMonth(month: Month): YearMonth {
        return YearMonth(year, month)
    }

    fun withMonth(month: Int): YearMonth? {
        return of(year, month)
    }

    fun withYear(year: Int): YearMonth {
        return YearMonth(year, month)
    }

    override fun compareTo(other: YearMonth): Int {
        return if (year == other.year) month.compareTo(other.month) else year.compareTo(other.year)
    }

    override fun equals(other: Any?): Boolean {
        return other is YearMonth && year == other.year && month == other.month
    }

    override fun toString(): String {
        return "$year-$month"
    }
}

fun Month.length(isLeapYear: Boolean): Int{
    return when(this){
        JANUARY -> 31
        FEBRUARY -> if(isLeapYear) 29 else 28
        MARCH -> 31
        APRIL -> 30
        MAY -> 31
        JUNE -> 30
        JULY -> 31
        AUGUST -> 31
        SEPTEMBER -> 30
        OCTOBER -> 31
        NOVEMBER -> 30
        DECEMBER -> 31
        else -> 31
    }
}

fun LocalDate.Companion.getDatesBetween(start: LocalDate, end: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var date = start
    while (date <= end) {
        dates.add(date)
        date = date.plus(DatePeriod(days = 1))
    }
    return dates
}

fun DateTimePeriod.toEpochMilliseconds(): Long {
    return (years * 365 + months * 30 + days) * 24 * 60 * 60 * 1000L
}