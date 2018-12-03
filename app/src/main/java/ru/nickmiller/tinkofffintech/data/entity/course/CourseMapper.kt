package ru.nickmiller.tinkofffintech.data.entity.course

import java.text.SimpleDateFormat
import java.util.*

val courseSDF = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
val courseDatePattern = "\\d{2}.\\d{2}.\\d{4}".toRegex()

fun CourseAbout.coursesCount(): Int {
    var count = 0
    delta.ops.forEach {
        if (it.insert is String && courseDatePattern.containsMatchIn(it.insert)) {
            count++
        }
    }
    return count
}


fun CourseAbout.coursesDates() = mutableListOf<Date>().apply {
    delta.ops.forEach {
        if (it.insert is String && courseDatePattern.containsMatchIn(it.insert)) {
            add(courseSDF.parse(it.insert))
        }
    }
}


fun Course.fullRaiting() =
    courseInfo?.firstOrNull {
        it.name?.contains("общий рейтинг", true) ?: false
    }?.grades


fun Course.getGradesOf(userId: Int?): Pair<Grade?, List<GroupedTask>?> {
    courseInfo?.forEach { courseInfo ->
        courseInfo.grades?.forEach { grade ->
            if (grade.studentId == userId &&
                grade.gradeMarks?.get(0)?.status != null
            ) {
                val tasks = courseInfo.groupedTasks?.flatten()
                return Pair(grade, tasks)
            }
        }
    }
    return Pair(null, null)
}
 
