package com.base.democalendarweek.monthmode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.democalendarweek.DaySelectedListener
import com.base.democalendarweek.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by NguyenLinh on 04,October,2024
 */
class MonthFragment : Fragment() {

    companion object {
        private const val ARG_MONTH_OFFSET = "month_offset"

        fun newInstance(monthOffset: Int, monthChangeListener: MonthChangeListener): MonthFragment {
            val fragment = MonthFragment()
            val args = Bundle()
            args.putInt(ARG_MONTH_OFFSET, monthOffset)
            fragment.arguments = args
            fragment.monthChangeListener = monthChangeListener
            return fragment
        }
    }

    private var monthChangeListener: MonthChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMonth)

        val monthOffset = arguments?.getInt(ARG_MONTH_OFFSET) ?: 0
        val daysOfMonth = getDaysOfMonth(monthOffset)

        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 7) // 7 columns for days of week
        recyclerView.adapter = MonthAdapter(daysOfMonth, object : DaySelectedListener {
            override fun onDaySelected(position: Int, dateSelected: String) {
                daysOfMonth[position].also { it.isSelected = !it.isSelected }
                recyclerView.adapter?.notifyItemChanged(position)
                monthChangeListener?.onDaySelected(dateSelected)
            }
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        callbackCurrentMonth()
    }

    private fun callbackCurrentMonth() {
        // calculator startOfMonth + endOfMonth
        val calendar = Calendar.getInstance()
        val monthOffset = arguments?.getInt(ARG_MONTH_OFFSET) ?: 0
        calendar.add(Calendar.MONTH, monthOffset)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonth =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time) // date
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, maxDay)
        val endOfMonth =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time) // date
        //callback result
        monthChangeListener?.onMonthChanged(startOfMonth, endOfMonth)
    }

    private fun getDaysOfMonth(monthOffset: Int): List<DayOfMonth> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, monthOffset)

        // Set to the first day of the month
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // Get day of the week for the 1st
        // Go back to the previous month's days to fill in the start if needed
        calendar.add(Calendar.DAY_OF_MONTH, -(firstDayOfWeek - 1))

        val daysOfMonth = mutableListOf<DayOfMonth>()
        for (i in 0 until 42) {
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val isCurrentMonth =
                calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
            daysOfMonth.add(DayOfMonth(day, isCurrentMonth))
            // Move to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return daysOfMonth
    }

    fun updateCalendarData(position: Int) {
        Log.d("updateCalendarData", position.toString())
    }

}
