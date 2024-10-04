package com.base.democalendarweek.monthmode

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.democalendarweek.DaySelectedListener
import com.base.democalendarweek.R

/**
 * Created by NguyenLinh on 04,October,2024
 */
class MonthAdapter(
    private val daysOfMonth: List<DayOfMonth>,
    private val callback: DaySelectedListener
) : RecyclerView.Adapter<MonthAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = daysOfMonth[position]
        holder.bind(day)
    }

    override fun getItemCount(): Int = daysOfMonth.size

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay: TextView = itemView.findViewById(R.id.tvDay)

        fun bind(day: DayOfMonth) {
            tvDay.text = day.day.toString()

            // Highlight current month days
            if (day.isCurrentMonth) {
                tvDay.setTextColor(Color.WHITE)
            } else {
                tvDay.setTextColor(Color.GRAY) // Different color for other months' days
            }
            if (day.isSelected){
                tvDay.setTextColor(Color.RED)
            }
            itemView.setOnClickListener {
                callback.onDaySelected(adapterPosition, day.day.toString())
            }
        }
    }
}
