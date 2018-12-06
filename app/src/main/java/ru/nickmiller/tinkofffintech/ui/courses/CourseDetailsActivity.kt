package ru.nickmiller.tinkofffintech.ui.courses

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course_details.*
import kotlinx.android.synthetic.main.view_toolbar.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.data.entity.course.coursesDates
import java.util.*

class CourseDetailsActivity : AppCompatActivity() {
    private val TASK_TYPE_FULL = "full"
    private val TASK_TYPE_TEST = "test_during_lecture"

    private val TASK_STATUS_ACCEPTED = "accepted"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val course = intent.getParcelableExtra<Course>(COURSE)
        title = course.title



        course.courseInfo?.firstOrNull {
            it.id != null &&
                    it.groupedTasks != null &&
                    it.grades != null &&
                    it.grades.firstOrNull { it.studentId == course.userId } != null
        }?.let { cInfo ->
            val userGrades = cInfo.grades?.firstOrNull { it.studentId == course.userId }
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

                testsPassed.text = "$testsAccepted/$testsAll"
                homeworkPassed.text = "$fullAccepted/$fullAll"

                testsProgress.progress = (testsAccepted.toFloat() / testsAll.toFloat() * 100).toInt()
                lecturesProgress.progress = (fullAccepted.toFloat() / fullAll.toFloat() * 100).toInt()


                //scores_total.text = sum.toString()
                //stat_progress.progress = sum.div(max).times(100).toInt()
            }
        }

        val coursesDates = course.courseAbout?.coursesDates()


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

        val allP = (passed.toFloat() / coursesDates!!.size.toFloat() * 100).toInt()
        allPassed.text = "$allP%"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private val COURSE = "course"

        fun start(context: Context, course: Course) {
            context.startActivity(Intent(context, CourseDetailsActivity::class.java).apply {
                putExtra(COURSE, course)
            })
        }
    }
}
