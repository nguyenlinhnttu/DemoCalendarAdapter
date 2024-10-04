package com.base.democalendarweek.weekmode

/**
 * Created by NguyenLinh on 04,October,2024
 */
interface WeekChangeListener {
    fun onWeekChanged(startOfWeek: String, endOfWeek: String)
    fun onDaySelected(daySelected: String)
}