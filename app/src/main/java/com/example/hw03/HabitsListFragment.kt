package com.example.hw03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_habits_list.*

class HabitsListFragment() : Fragment(), IHabitsListObserver {
    private val model: HabitsViewModel by activityViewModels()
    private val habits: MutableList<Habit> = mutableListOf()
    private lateinit var viewAdapter: HabitsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    companion object {
        fun newInstance(type: String) =
            HabitsListFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getString("type") ?: "Good"

        model.registerObserver(this).observe(viewLifecycleOwner, androidx.lifecycle.Observer {})
        model.habitsByType[this.type]?.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { reload(it) })

        viewAdapter = HabitsRecyclerViewAdapter(habits, requireActivity() as IEditHabitHandler)

        habits_list_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
        }
    }

    override var type: String = "Good"

    private fun reload(list: List<Habit>) {
        habits.clear()
        habits.addAll(list)
        viewAdapter.notifyDataSetChanged()
    }
}
