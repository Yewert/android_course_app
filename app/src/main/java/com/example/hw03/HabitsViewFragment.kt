package com.example.hw03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_habits_view.*


class HabitsViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        filter_unset.setOnClickListener {
            (requireActivity() as IFilterHandler).unsetFilter()
            habit_name_search.setText("")
            habit_description_search.setText("")
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        filter_set.setOnClickListener {
            val nameSearchQuery = habit_name_search.text
            val descriptionSearchQuery = habit_description_search.text
            (requireActivity() as IFilterHandler).setFilter {
                (!nameSearchQuery.isBlank() && it.name.contains(nameSearchQuery))
                        || (!descriptionSearchQuery.isBlank() && it.description.contains(
                    descriptionSearchQuery
                ))
            }
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val habitsListPagerAdapter = HabitsListPagerAdapter(requireActivity())
        pager.adapter = habitsListPagerAdapter
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Good"
                else -> "Bad"
            }
        }.attach()

        habits_list_add.setOnClickListener {
            (requireActivity() as INewHabitHandler).handleNew()
        }
    }
}