package ru.nickmiller.tinkofffintech.ui.courses.progress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_personal_stat.*
import kotlinx.android.synthetic.main.view_toolbar.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.Grade
import ru.nickmiller.tinkofffintech.data.entity.course.GradeMark
import ru.nickmiller.tinkofffintech.data.entity.course.GroupedTask

class PersonalStatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_stat)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Оценки"

        val grade = intent.getParcelableExtra<Grade>(GRADE)
        val tasks = intent.getParcelableArrayListExtra<GroupedTask>(TASKS)

        val list = mutableListOf<Pair<String, GradeMark>>()
        if (grade.gradeMarks?.size == tasks.size) {
            for (i in 0 until tasks.size) {
                if (tasks[i].title != null && grade.gradeMarks[i].status != null) {
                    list.add(Pair(tasks[i].title!!, grade.gradeMarks[i]))
                }
            }
        }
        val adapter = MarksAdapter(list.reversed())
        marksRecycler.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val GRADE = "grade"
        private const val TASKS = "tasks"

        fun start(context: Context, grade: Grade, tasks: ArrayList<GroupedTask>) {
            context.startActivity(Intent(context, PersonalStatActivity::class.java).apply {
                putExtra(GRADE, grade)
                putParcelableArrayListExtra(TASKS, tasks)
            })
        }
    }
}
