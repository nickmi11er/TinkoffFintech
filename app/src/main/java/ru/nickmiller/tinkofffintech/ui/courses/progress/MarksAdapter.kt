package ru.nickmiller.tinkofffintech.ui.courses.progress

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_grade_mark.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.GradeMark
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder


class MarksAdapter(var marks: List<Pair<String, GradeMark>>) : RecyclerView.Adapter<MarksAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_grade_mark, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(marks[position])
    }

    override fun getItemCount() = marks.size


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class ViewHolder(itemView: View) : BaseViewHolder<Pair<String, GradeMark>>(itemView) {

        override fun bind(item: Pair<String, GradeMark>) {
            taskName.text = item.first
            gradeStatus.text = getStatus(item.second.status)
            gradeMark.bind(item.second)
        }

        private fun getStatus(stat: String?): String =
            "Статус: " + when (stat) {
                "new" -> "Новый"
                "on_check" -> "На проверке"
                "need_work" -> "На доработке"
                "accepted" -> "Зачтено"
                "failed" -> "Не зачтено"
                else -> "Неизвестно"
            }
    }
}
 
 
