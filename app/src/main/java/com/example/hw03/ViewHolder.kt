package com.example.hw03

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.habit_name)
    private val description: TextView = itemView.findViewById(R.id.habit_description)
    private val type: TextView = itemView.findViewById(R.id.habit_type)
    private val repetitionsAndPeriod: TextView =
        itemView.findViewById(R.id.habit_repetitions_and_period)
    private val priority: TextView = itemView.findViewById(R.id.habit_priority)
    private lateinit var habit: Habit
    private var index = -1
    private lateinit var clickHandler: IEditHabitHandler

    fun bind(
        habit: Habit,
        index: Int,
        clickHandler: IEditHabitHandler
    ) {
        this.habit = habit
        this.index = index
        this.clickHandler = clickHandler
        name.text = habit.name
        type.text = habit.type
        priority.text = "Priority ${habit.priority}"
        repetitionsAndPeriod.text = "${habit.repetitions} times every ${habit.period} days"
        description.text = habit.description

        itemView.setOnClickListener { clickHandler.handleEdit(habit) }
    }


}