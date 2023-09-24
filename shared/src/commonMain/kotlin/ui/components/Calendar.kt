package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.tkhs.handaimanager.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
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
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import util.YearMonth

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    date: YearMonth,
    highlight: List<LocalDate> = listOf(),
    showToday: Boolean = true,
    onDateChange: (YearMonth) -> Unit = {},
    onClicked: (LocalDate) -> Unit = {},
) {
    Box(modifier = modifier){
        Column(verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top), modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
            CalendarHeader(date, onDateChange)
            CalendarBody(date, highlight, showToday, onClicked)
        }
    }
}

@Composable
private fun CalendarHeader(date: YearMonth, onDateChange: (YearMonth) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().height(24.dp)) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxHeight()) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.width(105.dp)) {
                Text(text = date.month.getDisplayName(), style = CalendarDefaults.headerMonthTextStyle)
                Text(text = date.year.toString(), style = CalendarDefaults.headerYearTextStyle)
            }
        }
        IconButton(onClick = { onDateChange(date.minus(DatePeriod(months = 1))) }) {
            Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "Previous month")
        }
        IconButton(onClick = { onDateChange(date.plus(DatePeriod(months = 1))) }) {
            Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = "Next month")
        }
    }
}

@Composable
private fun CalendarBody(date: YearMonth, highlight: List<LocalDate>, showToday: Boolean, onClicked: (LocalDate) -> Unit = {}) {
    data class Day(val day: LocalDate, val isOutOfMonth: Boolean)

    val days: List<Day> = date.atDay(1).let { it.minus(DatePeriod(days = it.dayOfWeek.ordinal)) }
        .let { start ->
            (0..41).map {
                val day = start.plus(DatePeriod(days = it))
                Day(day, day.month != date.month)
            }
        }
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween){
        for(i in 0..6){
            Column(modifier = Modifier.wrapContentHeight(), verticalArrangement = Arrangement.spacedBy(2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = when(i)
                    {
                        0 -> "MON"
                        1 -> "TUE"
                        2 -> "WED"
                        3 -> "THU"
                        4 -> "FRI"
                        5 -> "SAT"
                        6 -> "SUN"
                        else -> ""
                    },
                    style = CalendarDefaults.bodyWeekHeaderTextStyle)
                val num = if(days[35].isOutOfMonth) 4 else 5
                for(j in 0..num){
                    val day = days[i + j * 7]
                    val isSelected = highlight.contains((day.day))
                    val isToday = showToday && day.day == Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                    IconButton(
                        onClick = { onClicked(day.day) },
                        modifier = Modifier.background(color = if(isSelected) CalendarDefaults.highlightColor else Color.Transparent, shape = CircleShape)
                            .border(2.dp, color = if(isToday) CalendarDefaults.highlightColor else Color.Transparent, shape = CircleShape))
                    {
                        Text(
                            text = day.day.dayOfMonth.toString(),
                            style = if(day.isOutOfMonth){
                                        CalendarDefaults.bodyDayOutOfMonthTextStyle
                                    }else if(isSelected){
                                        CalendarDefaults.highlightTextStyle
                                    }else {
                                        CalendarDefaults.bodyDayTextStyle
                                    },
                        )
                    }
                }
            }
        }
    }
}

private fun Month.getDisplayName(): String{
    return when(this){
        JANUARY -> "Jan"
        FEBRUARY -> "Feb"
        MARCH -> "Mar"
        APRIL -> "Apr"
        MAY -> "May"
        JUNE -> "Jun"
        JULY -> "Jul"
        AUGUST -> "Aug"
        SEPTEMBER -> "Sep"
        OCTOBER -> "Oct"
        NOVEMBER -> "Nov"
        DECEMBER -> "Dec"
        else -> ""
    }
}

object CalendarDefaults{
    private val baseTextStyle:TextStyle @Composable get() = TextStyle(fontFamily = fontFamilyResource(MR.fonts.Inter.regular))
    val headerYearTextStyle:TextStyle @Composable get() = baseTextStyle.merge(fontSize = 20.sp)
    val headerMonthTextStyle:TextStyle @Composable get() = headerYearTextStyle.merge(fontWeight = FontWeight(700))
    val bodyWeekHeaderTextStyle:TextStyle @Composable get() = baseTextStyle.merge(fontSize = 12.sp, color = Color.Gray)
    val bodyDayTextStyle:TextStyle @Composable get() = baseTextStyle.merge(fontSize = 15.sp)
    val bodyDayOutOfMonthTextStyle:TextStyle @Composable get() = bodyDayTextStyle.merge(color = Color.LightGray)

    val highlightColor:Color @Composable get() = Color(0xFFFF7B7B)
    val highlightTextStyle:TextStyle @Composable get() = bodyDayTextStyle.merge(color = Color.White, fontWeight = FontWeight(700))
}