package com.example.hw03

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class HabitsListPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HabitsListFragment.newInstance("Good")
            else -> HabitsListFragment.newInstance("Bad")
        }
    }

    override fun getItemCount() = 2
}