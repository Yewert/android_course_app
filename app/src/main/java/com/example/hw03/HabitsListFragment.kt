package com.example.hw03

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class HabitsListFragment() : Fragment(), IHabitsListObserver {
    private val model: HabitsViewModel by activityViewModels()
    private lateinit var habits: MutableList<Habit>
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


        (requireActivity() as IHabitsListObservable).attachObserver(this)
//        if (savedInstanceState != null)
//            return

        habits = model.getHabits(type).toMutableList()

        viewAdapter = HabitsRecyclerViewAdapter(habits, requireActivity() as IEditHabitHandler)

        view.findViewById<RecyclerView>(R.id.habits_list_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
        }
    }

    override var type: String = "Good"

    override fun onHabitChanged(id: UUID) {
        val index =
            habits.withIndex().find { indexedValue -> indexedValue.value.id == id }?.index ?: -1
        if (index == -1) {
            habits.add(model.findById(id)!!)
            viewAdapter.notifyItemInserted(habits.size - 1)
        } else {
            habits[index] = model.findById(id)!!
            viewAdapter.notifyItemChanged(index)
        }
    }

    override fun onHabitRemoved(id: UUID) {
        val index =
            habits.withIndex().find { indexedValue -> indexedValue.value.id == id }?.index ?: -1
        if (index != -1) {
            habits.removeAt(index)
            viewAdapter.notifyItemRemoved(index)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}
