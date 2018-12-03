package ru.nickmiller.tinkofffintech.ui.courses.progress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_progress_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.data.entity.course.Grade
import ru.nickmiller.tinkofffintech.data.entity.course.fullRaiting
import ru.nickmiller.tinkofffintech.data.entity.course.getGradesOf


class ProgressListActivity : AppCompatActivity() {
    private var sortDescending = true
    lateinit var adapter: ProgressListAdapter
    lateinit var course: Course
    var grades: List<Grade>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Успеваемость"
        course = intent.getParcelableExtra<Course>(COURSE)
        grades = course.fullRaiting()

        init()
    }

    fun init() {
        adapter = ProgressListAdapter(grades!!.sortedByDescending { it.gradeMarks?.get(0)?.mark })
        adapter.setOnStudentClickListener {
            val (grade, tasks) = course.getGradesOf(it.studentId)
            if (grade != null && tasks != null) {
                PersonalStatActivity.start(this, grade, ArrayList(tasks))
            } else {
                Toast.makeText(this, getString(R.string.error_null_user_info), Toast.LENGTH_SHORT).show()
            }
        }
        progressRecycler.adapter = adapter

        search_view.setHint(getString(R.string.msg_search))
        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter?.filter(newText)
                return false
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_progress_list, menu)
        val item = menu.findItem(R.id.actionSearch)
        search_view.setMenuItem(item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemSort -> showSortPrefPopup()
            android.R.id.home -> onBackPressed()
        }
        return true
    }


    private fun showSortPrefPopup() {
        val popup = PopupMenu(this, findViewById(R.id.itemSort))
        popup.inflate(R.menu.popup_progress_sort)
        if (sortDescending) {
            popup.menu.findItem(R.id.ratingDesc).isChecked = true
        } else {
            popup.menu.findItem(R.id.ratingAsc).isChecked = true
        }

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ratingDesc -> sortDescending = true
                R.id.ratingAsc -> sortDescending = false
            }
            adapter.sortByGrades(sortDescending)
            true
        }
        popup.show()
    }


    companion object {
        private val COURSE = "course"

        fun start(context: Context, course: Course) {
            context.startActivity(Intent(context, ProgressListActivity::class.java).apply {
                putExtra(COURSE, course)
            })
        }
    }
}
