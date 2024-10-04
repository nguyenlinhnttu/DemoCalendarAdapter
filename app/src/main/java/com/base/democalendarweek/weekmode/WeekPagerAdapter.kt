package com.base.democalendarweek.weekmode

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.democalendarweek.Constants
import com.base.democalendarweek.monthmode.MonthFragment

/**
 * Created by NguyenLinh on 04,October,2024
 */
class WeekPagerAdapter(
    fragment: Fragment,
    private val weekChangeListener: WeekChangeListener
) : FragmentStateAdapter(fragment) {
    private val fragmentList = hashMapOf<Int, WeekFragment?>()
    override fun getItemCount(): Int = Constants.MAX_WEEK

    override fun createFragment(position: Int): Fragment {
        val fragment =
            WeekFragment.newInstance(position - (Constants.MAX_MONTH / 2), weekChangeListener)
        fragmentList[position] = fragment
        return fragment
    }

    fun getFragment(position: Int): WeekFragment? {
        return fragmentList.getValue(position)
    }
}