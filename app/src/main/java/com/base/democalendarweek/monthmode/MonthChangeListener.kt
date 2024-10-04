package com.base.democalendarweek.monthmode

/**
 * Created by NguyenLinh on 04,October,2024
 */
interface MonthChangeListener {
    fun onMonthChanged(startOfMonth: String, endOfMonth: String)
    fun onDaySelected(daySelected: String)
}
