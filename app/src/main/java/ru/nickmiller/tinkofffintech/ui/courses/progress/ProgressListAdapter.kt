package ru.nickmiller.tinkofffintech.ui.courses.progress

import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import kotlinx.android.synthetic.main.view_personal_progress_hor.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.Grade
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder
import ru.nickmiller.tinkofffintech.utils.softColorsArray


class ProgressListAdapter(var grades: List<Grade>) :
    RecyclerView.Adapter<ProgressListAdapter.ProgressListViewHolder>(), Filterable {

    private val iconColors = hashMapOf<Int, Int>()
    private var onStudentClickListener: ((grade: Grade) -> Unit)? = null
    private var gradesFiltered: List<Grade> = grades

    init {
        grades.forEach { grade ->
            grade.studentId?.let { iconColors[it] = softColorsArray()[(0 until 5).random() * 7 % 5] }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProgressListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_personal_progress_hor,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProgressListViewHolder, position: Int) {
        val grade = gradesFiltered[position]
        holder.bind(grade)
        holder.itemView.setOnClickListener {
            onStudentClickListener?.invoke(grade)
        }
    }

    override fun getItemCount() = gradesFiltered.size

    fun sortByGrades(descending: Boolean = true) {
        grades = if (descending) {
            grades.sortedByDescending { it.gradeMarks?.get(0)?.mark }
        } else {
            grades.sortedBy { it.gradeMarks?.get(0)?.mark }
        }
        gradesFiltered = grades
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter? {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                gradesFiltered = if (charString.isEmpty()) {
                    grades
                } else {
                    grades.filter {
                        it.student?.contains(charString, true) ?: false
                    }
                }
                return FilterResults().apply { values = gradesFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                gradesFiltered = results?.values as List<Grade>
                notifyDataSetChanged()
            }
        }
    }


    fun setOnStudentClickListener(listener: (grade: Grade) -> Unit) {
        this.onStudentClickListener = listener
    }


    inner class ProgressListViewHolder(itemView: View) : BaseViewHolder<Grade>(itemView) {

        override fun bind(item: Grade) {
            username.text = item.student
            userIcon.text = (gradesFiltered.indexOf(item) + 1).toString() //item.student?.get(0).toString()
            DrawableCompat.setTint(
                userIcon.background,
                ContextCompat.getColor(itemView.context, iconColors[item.studentId] ?: R.color.colorBlueSoft)
            )
            userScores.text = String.format("%.2f балла", item.gradeMarks?.get(0)?.mark)
        }
    }

}