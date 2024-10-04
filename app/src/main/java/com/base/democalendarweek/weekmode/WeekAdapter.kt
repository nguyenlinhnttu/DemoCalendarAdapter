package com.base.democalendarweek.weekmode

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.alpha
import androidx.recyclerview.widget.RecyclerView
import com.base.democalendarweek.DaySelectedListener
import com.base.democalendarweek.R

class WeekAdapter(
    private val daysOfWeek: List<DayOfWeek>,
    private val callback: DaySelectedListener
) : RecyclerView.Adapter<WeekAdapter.WeekViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_item, parent, false)
        return WeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val day = daysOfWeek[position]
        holder.bind(day)
        //Fix size item full screen width
        val screenWidth = getScreenWidth()
        val itemWidth = screenWidth / 7
        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = itemWidth
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = daysOfWeek.size

    inner class WeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDayOfWeek: TextView = itemView.findViewById(R.id.tvDayOfWeek)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(day: DayOfWeek) {
            tvDayOfWeek.text = day.dayName
            tvDate.text = day.date
            if (day.isSelected) {
                itemView.rootView.setBackgroundColor(Color.RED)
            } else {
                itemView.rootView.setBackgroundColor(Color.TRANSPARENT)
            }
            itemView.setOnClickListener {
                callback.onDaySelected(adapterPosition, day.date)
            }
        }
    }

    private fun getScreenWidth(): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return displayMetrics.widthPixels
    }
}
