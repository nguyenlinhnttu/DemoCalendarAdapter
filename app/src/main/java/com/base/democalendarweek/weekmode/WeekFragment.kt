package com.base.democalendarweek.weekmode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.democalendarweek.R
import com.base.democalendarweek.DaySelectedListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeekFragment : Fragment() {
    private var daysOfWeek: List<DayOfWeek> = emptyList()
    private var weekChangeListener: WeekChangeListener? = null

    companion object {
        private const val ARG_WEEK_OFFSET = "week_offset"

        fun newInstance(weekOffset: Int, weekChangeListener: WeekChangeListener): WeekFragment {
            val fragment = WeekFragment()
            val args = Bundle()
            args.putInt(ARG_WEEK_OFFSET, weekOffset)
            fragment.arguments = args
            fragment.weekChangeListener = weekChangeListener
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        //Callback value
        if (daysOfWeek.isNotEmpty()) {
            val startOfWeek = daysOfWeek.first().date
            val endOfWeek = daysOfWeek.last().date
            weekChangeListener?.onWeekChanged(startOfWeek, endOfWeek)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_week, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewWeek)

        val weekOffset = arguments?.getInt(ARG_WEEK_OFFSET) ?: 0
        daysOfWeek = getDaysOfWeek(weekOffset)

        //display week
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = WeekAdapter(daysOfWeek, object : DaySelectedListener {
            override fun onDaySelected(position: Int, dateSelected: String) {
                daysOfWeek[position].also { it.isSelected = !it.isSelected }
                recyclerView.adapter?.notifyItemChanged(position)
                weekChangeListener?.onDaySelected(dateSelected)
            }
        })

        return view
    }

    private fun getDaysOfWeek(weekOffset: Int): List<DayOfWeek> {
        val calendar = Calendar.getInstance()
        //weekOffset == 0 current week
        //weekOffset = +1 next week
        //weekOffset = -1 previous week
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)
        // start week is sunday
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        val daysOfWeek = mutableListOf<DayOfWeek>()
        for (i in 0..6) {
            val dayName = SimpleDateFormat("E", Locale.getDefault()).format(calendar.time) // name
            val date = SimpleDateFormat("dd/MM", Locale.getDefault()).format(calendar.time) // date
            daysOfWeek.add(DayOfWeek(dayName, date))
            calendar.add(Calendar.DAY_OF_MONTH, 1) // add date
        }
        return daysOfWeek
    }

    fun updateCalendarData(position: Int) {
        Log.d("updateCalendarData", position.toString())
    }

}
