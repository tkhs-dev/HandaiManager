package util

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class YearMonthTest {
    @Test
    fun now() {
        YearMonth.now().let {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            assertTrue(it.year == now.year && it.month == now.month)
        }
    }

    @Test
    fun of() {
        YearMonth.of(2021, Month.JANUARY).let {
            assertTrue(it.year == 2021 && it.month == Month.JANUARY)
        }
        assertNotNull(YearMonth.of(2021, 1))
        assertNull(YearMonth.of(2021, 13))
    }

    @Test
    fun atEndOfMonth() {
        YearMonth.of(2021, Month.JANUARY).atEndOfMonth().let {
            assertTrue(it.year == 2021 && it.month == Month.JANUARY && it.dayOfMonth == 31)
        }
        YearMonth.of(2021, Month.FEBRUARY).atEndOfMonth().let {
            assertTrue(it.year == 2021 && it.month == Month.FEBRUARY && it.dayOfMonth == 28)
        }
        YearMonth.of(2020, Month.FEBRUARY).atEndOfMonth().let {
            assertTrue(it.year == 2020 && it.month == Month.FEBRUARY && it.dayOfMonth == 29)
        }
        YearMonth.of(2021, Month.DECEMBER).atEndOfMonth().let {
            assertTrue(it.year == 2021 && it.month == Month.DECEMBER && it.dayOfMonth == 31)
        }
    }

    @Test
    fun isLeapYear() {
        assertTrue(YearMonth.of(2000, Month.JANUARY).isLeapYear())
        assertTrue(YearMonth.of(2004, Month.JANUARY).isLeapYear())
        assertFalse(YearMonth.of(2100, Month.JANUARY).isLeapYear())
        assertFalse(YearMonth.of(2003, Month.JANUARY).isLeapYear())
    }

    @Test
    fun isValidDay() {
        assertTrue(YearMonth.of(2021, Month.JANUARY).isValidDay(1))
        assertTrue(YearMonth.of(2021, Month.JANUARY).isValidDay(31))
        assertFalse(YearMonth.of(2021, Month.JANUARY).isValidDay(0))
        assertFalse(YearMonth.of(2021, Month.JANUARY).isValidDay(32))
        assertTrue(YearMonth.of(2021, Month.FEBRUARY).isValidDay(1))
        assertTrue(YearMonth.of(2021, Month.FEBRUARY).isValidDay(28))
        assertFalse(YearMonth.of(2021, Month.FEBRUARY).isValidDay(0))
        assertFalse(YearMonth.of(2021, Month.FEBRUARY).isValidDay(29))
        assertTrue(YearMonth.of(2020, Month.FEBRUARY).isValidDay(29))
    }

    @Test
    fun lengthOfMonth() {
        assertEquals(31, YearMonth.of(2021, Month.JANUARY).lengthOfMonth())
        assertEquals(28, YearMonth.of(2021, Month.FEBRUARY).lengthOfMonth())
        assertEquals(31, YearMonth.of(2021, Month.MARCH).lengthOfMonth())
        assertEquals(30, YearMonth.of(2021, Month.APRIL).lengthOfMonth())
        assertEquals(31, YearMonth.of(2021, Month.MAY).lengthOfMonth())
        assertEquals(30, YearMonth.of(2021, Month.JUNE).lengthOfMonth())
        assertEquals(31, YearMonth.of(2021, Month.JULY).lengthOfMonth())
        assertEquals(31, YearMonth.of(2021, Month.AUGUST).lengthOfMonth())
        assertEquals(30, YearMonth.of(2021, Month.SEPTEMBER).lengthOfMonth())
        assertEquals(31, YearMonth.of(2021, Month.OCTOBER).lengthOfMonth())
        assertEquals(30, YearMonth.of(2021, Month.NOVEMBER).lengthOfMonth())
        assertEquals(31, YearMonth.of(2021, Month.DECEMBER).lengthOfMonth())
        assertEquals(29, YearMonth.of(2020, Month.FEBRUARY).lengthOfMonth())
    }

    @Test
    fun lengthOfYear() {
        assertEquals(365, YearMonth.of(2021, Month.JANUARY).lengthOfYear())
        assertEquals(366, YearMonth.of(2020, Month.JANUARY).lengthOfYear())
    }

    @Test
    fun minus() {
        assertEquals(YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.FEBRUARY) - DatePeriod(0, 1))
        assertEquals(YearMonth.of(2021, Month.NOVEMBER), YearMonth.of(2021, Month.DECEMBER) - DatePeriod(0, 1))
        assertEquals(YearMonth.of(2020, Month.JANUARY), YearMonth.of(2021, Month.JANUARY) - DatePeriod(1, 0))
        assertEquals(YearMonth.of(2020, Month.DECEMBER), YearMonth.of(2021, Month.JANUARY) - DatePeriod(0, 1))
        assertEquals(YearMonth.of(2019, Month.DECEMBER), YearMonth.of(2021, Month.JANUARY) - DatePeriod(1, 1))
        assertEquals(YearMonth.of(2020, Month.JANUARY), YearMonth.of(2021, Month.JANUARY) - DatePeriod(0, 12))
        assertEquals(YearMonth.of(2019, Month.JANUARY), YearMonth.of(2021, Month.JANUARY) - DatePeriod(1, 12))
        assertEquals(YearMonth.of(2018, Month.DECEMBER), YearMonth.of(2021, Month.JANUARY) - DatePeriod(1, 13))
    }

    @Test
    fun plus() {
        assertEquals(YearMonth.of(2021, Month.FEBRUARY), YearMonth.of(2021, Month.JANUARY) + DatePeriod(0, 1))
        assertEquals(YearMonth.of(2021, Month.DECEMBER), YearMonth.of(2021, Month.NOVEMBER) + DatePeriod(0, 1))
        assertEquals(YearMonth.of(2022, Month.JANUARY), YearMonth.of(2021, Month.JANUARY) + DatePeriod(1, 0))
        assertEquals(YearMonth.of(2022, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER) + DatePeriod(0, 1))
        assertEquals(YearMonth.of(2022, Month.JANUARY), YearMonth.of(2020, Month.DECEMBER) + DatePeriod(1, 1))
        assertEquals(YearMonth.of(2021, Month.DECEMBER), YearMonth.of(2020, Month.DECEMBER) + DatePeriod(0, 12))
        assertEquals(YearMonth.of(2022, Month.DECEMBER), YearMonth.of(2020, Month.DECEMBER) + DatePeriod(1, 12))
        assertEquals(YearMonth.of(2023, Month.JANUARY), YearMonth.of(2020, Month.DECEMBER) + DatePeriod(1, 13))
    }

    @Test
    fun withMonth() {
        assertEquals(YearMonth.of(2021, Month.FEBRUARY), YearMonth.of(2021, Month.JANUARY).withMonth(Month.FEBRUARY))
    }

    @Test
    fun withYear() {
        assertEquals(YearMonth.of(2022, Month.JANUARY), YearMonth.of(2021, Month.JANUARY).withYear(2022))
    }

    @Test
    fun compareTo() {
        assertTrue(YearMonth.of(2021, Month.JANUARY).compareTo(YearMonth.of(2021, Month.JANUARY)) == 0)
        assertTrue(YearMonth.of(2021, Month.JANUARY) < YearMonth.of(2021, Month.FEBRUARY))
        assertTrue(YearMonth.of(2021, Month.JANUARY) < YearMonth.of(2022, Month.JANUARY))
    }
}