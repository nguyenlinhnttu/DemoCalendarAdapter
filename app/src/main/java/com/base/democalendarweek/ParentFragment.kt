package com.base.democalendarweek

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.base.democalendarweek.monthmode.MonthChangeListener
import com.base.democalendarweek.monthmode.MonthFragment
import com.base.democalendarweek.monthmode.MonthPagerAdapter
import com.base.democalendarweek.weekmode.WeekChangeListener
import com.base.democalendarweek.weekmode.WeekPagerAdapter

/**
 * Created by NguyenLinh on 04,October,2024
 */
class ParentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_parent, container, false)
        //1. Display mode week
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
        val tvWeek: TextView = view.findViewById(R.id.tvWeek)
        val adapter = WeekPagerAdapter(
            this,
            object : WeekChangeListener {
                override fun onWeekChanged(startOfWeek: String, endOfWeek: String) {
                    tvWeek.text = "1. Week Mode : $startOfWeek-$endOfWeek"
                }

                override fun onDaySelected(daySelected: String) {
                    Log.d("onDaySelected", daySelected)
                }
            })
        viewPager.adapter = adapter
        // Set center is current week
        viewPager.setCurrentItem(Constants.MAX_WEEK / 2, false)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val currentFragment = adapter.getFragment(position)
                currentFragment?.updateCalendarData(position)
            }
        })
        //2.Display Mode Month
        val viewPager2: ViewPager2 = view.findViewById(R.id.viewPager2)
        val tvMonth: TextView = view.findViewById(R.id.tvMonth)
        val adapter2 = MonthPagerAdapter(this, object : MonthChangeListener {
            override fun onMonthChanged(startOfMonth: String, endOfMonth: String) {
                tvMonth.text = "2. Month Mode : $startOfMonth-$endOfMonth"
                Log.d("ParentFragment", "CurrentMonth: $startOfMonth - $endOfMonth")
            }

            override fun onDaySelected(daySelected: String) {
                Log.d("onDaySelected", daySelected)
            }
        })
        viewPager2.adapter = adapter2
        // Set ViewPager to the current month
        viewPager2.setCurrentItem(Constants.MAX_MONTH / 2, false)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val currentFragment = adapter2.getFragment(position)
                currentFragment?.updateCalendarData(position)
            }
        })
        return view
    }
}
