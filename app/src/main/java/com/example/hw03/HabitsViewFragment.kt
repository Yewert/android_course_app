package com.example.hw03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator

class HabitsViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitsListPagerAdapter = HabitsListPagerAdapter(requireActivity())
        val pager = view.findViewById<ViewPager2>(R.id.pager)
        pager.adapter = habitsListPagerAdapter
        TabLayoutMediator(view.findViewById(R.id.tabs), pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Good"
                else -> "Bad"
            }
        }.attach()

        view.findViewById<FloatingActionButton>(R.id.habits_list_add).setOnClickListener {
            (requireActivity() as INewHabitHandler).handleNew()
        }
    }
}