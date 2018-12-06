package ru.nickmiller.tinkofffintech.ui.courses

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_course_stats.view.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.ui.courses.progress.ProgressListActivity


class CoursesAdapter(val courses: List<Course>) : RecyclerView.Adapter<CoursesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder =
        ActiveCoursesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_course_stats, parent, false)
        )

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bind(courses[position])
        holder.itemView.btnCourseDetails.setOnClickListener {
            CourseDetailsActivity.start(it.context, courses[position])
        }
        holder.itemView.btnCourseProgress.setOnClickListener {
            ProgressListActivity.start(holder.itemView.context, courses[position])
        }
    }

    override fun getItemCount() = courses.size
}