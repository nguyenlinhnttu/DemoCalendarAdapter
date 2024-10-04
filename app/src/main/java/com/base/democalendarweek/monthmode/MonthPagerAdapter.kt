package com.base.democalendarweek.monthmode

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.democalendarweek.Constants

/**
 * Created by NguyenLinh on 04,October,2024
 */
class MonthPagerAdapter(
    fragment: Fragment,
    private val monthChangeListener: MonthChangeListener
) : FragmentStateAdapter(fragment) {
    private val fragmentList = hashMapOf<Int,MonthFragment?>()
    override fun getItemCount(): Int = Constants.MAX_MONTH

    override fun createFragment(position: Int): Fragment {
        val fragment = MonthFragment.newInstance(position - (Constants.MAX_MONTH / 2), monthChangeListener)
        fragmentList[position] = fragment
        return fragment
    }

    fun getFragment(position: Int): MonthFragment? {
        return fragmentList.getValue(position)
    }
}
