package ru.nickmiller.tinkofffintech.ui.courses

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.view_course_stats.*
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.data.entity.course.coursesCount
import ru.nickmiller.tinkofffintech.data.entity.course.coursesDates
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder
import ru.nickmiller.tinkofffintech.utils.suffixOfNum
import java.util.*


private const val TASK_TYPE_FULL = "full"
private const val TASK_TYPE_TEST = "test_during_lecture"

private const val TASK_STATUS_ACCEPTED = "accepted"

abstract class CoursesViewHolder(itemView: View) : BaseViewHolder<Course>(itemView)


class ActiveCoursesViewHolder(itemView: View) : CoursesViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bind(item: Course) {
        item.courseInfo?.firstOrNull {
            it.id != null &&
                    it.groupedTasks != null &&
                    it.grades != null &&
                    it.grades.firstOrNull { it.studentId == item.userId } != null
        }?.let { cInfo ->
            val userGrades = cInfo.grades?.firstOrNull { it.studentId == item.userId }
            if (userGrades != null) {

                var testsAll = 0
                var testsAccepted = 0
                var fullAll = 0
                var fullAccepted = 0

                userGrades.gradeMarks?.forEach {
                    if (it.taskType == TASK_TYPE_FULL) {
                        fullAll++
                        if (it.status == TASK_STATUS_ACCEPTED) {
                            fullAccepted++
                        }
                    } else if (it.taskType == TASK_TYPE_TEST) {
                        testsAll++
                        if (it.status == TASK_STATUS_ACCEPTED) {
                            testsAccepted++
                        }
                    }
                }

                user_tests.text = "$testsAccepted/$testsAll"
                user_full.text = "$fullAccepted/$fullAll"

                val sum = userGrades.gradeMarks?.map { it.mark }?.last() ?: 0.0
                val max = cInfo.groupedTasks?.flatten()?.sumByDouble { it.maxScore ?: 0.0 } ?: 0.0

                scores_total.text = sum.toString()
                stat_progress.progress = sum.div(max).times(100).toInt()
            }
        }

        val coursesDates = item.courseAbout?.coursesDates()
        stat_works_count.text =
                "${item.courseAbout?.coursesCount()} заняти${suffixOfNum(coursesDates?.size)}"


        val cal = Calendar.getInstance()
        val currentDate = Date()
        var passed = 0
        coursesDates?.forEach {
            cal.time = it
            cal.set(Calendar.HOUR, 22)
            if (currentDate.after(it)) {
                passed++
            }
        }

        progress.progress = (passed.toFloat() / coursesDates!!.size.toFloat() * 100).toInt()
        tasksPassed.text = "$passed заняти${suffixOfNum(passed)}"
        tasksLeft.text = "${coursesDates.size - passed} заняти${suffixOfNum(coursesDates!!.size - passed)}"


        item.courseInfo?.firstOrNull {
            it.name?.contains("общий рейтинг", true) ?: false
        }.let { cInfo ->
            val sorted = cInfo?.grades?.sortedByDescending { it.gradeMarks?.get(0)?.mark }
            val userPos = sorted?.indexOfFirst { it.studentId == item.userId }?.plus(1)
            val totalCount = sorted?.size

            user_position.text = "$userPos/$totalCount"
        }
    }
}
 
 
