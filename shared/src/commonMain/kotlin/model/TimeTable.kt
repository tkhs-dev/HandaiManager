package model

import kotlinx.datetime.DayOfWeek

data class TimeTable(val year:Int, val term:Term, val classes:List<DayTimeTable>){
    sealed class TimeTableClass{
        data class Class(val id:String, val name:String?, val teacher:String?, val room:List<String>?):TimeTableClass()
        object Empty:TimeTableClass()
    }

    enum class Term{
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER;

        companion object{
            fun fromJpText(text:String):Term?{
                return when(text){
                    "春学期" -> Term.SPRING
                    "夏学期" -> Term.SUMMER
                    "秋学期" -> Term.AUTUMN
                    "冬学期" -> Term.WINTER
                    else -> null
                }
            }
        }
    }

    data class DayTimeTable(val day:DayOfWeek, val classes:List<TimeTableClass>)
}