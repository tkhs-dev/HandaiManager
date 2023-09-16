package util

import kotlinx.datetime.DayOfWeek
import ktsoup.KtSoupParser
import model.TimeTable

object HtmlUtil {
    fun parseTimeTable(html:String): TimeTable? {
        val list =
            Regex("""(\d{4})年度　(春学期|夏学期|秋学期|冬学期)""").find(html)?.groupValues ?: return null
        if (list.size != 3) return null
        val year = list[1].toIntOrNull() ?: return null
        val term = TimeTable.Term.fromJpText(list[2]) ?: return null
        val parser = KtSoupParser.parse(html)
        val table = parser.getElementsByClass("rishu-koma-inner")
        if (table.size != 42) return null

        val classes = table.map {
            it.child(1)?.children()
        }.map {
            val id: String = it?.get(0)?.textContent() ?: return@parseTimeTable null
            if (id == "未登録") {
                TimeTable.TimeTableClass.Empty
            } else {
                val tar = it[4].child(1)?.children()?.filterIndexed{ index, _ -> index % 2 == 0}?.map { it.textContent() }
                TimeTable.TimeTableClass.Class(
                    id = id,
                    name = it[2].textContent(),
                    teacher = tar?.get(0),
                    room = tar?.drop(1)
                )
            }
        }.withIndex()
        .groupBy (keySelector = {it.index % 6}, valueTransform = {it.value} )
        .entries
        .map {
            TimeTable.DayTimeTable(DayOfWeek.values().get(it.key), it.value)
        }

        return TimeTable(year, term, classes)
    }
}