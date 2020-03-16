package com.example.hw03

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.habit_name)
    private val description: TextView = itemView.findViewById(R.id.habit_description)
    private val priority: TextView = itemView.findViewById(R.id.habit_priority)
    fun bind(
        habit: Habit,
        position: Int,
        clickHandler: ItemClickHandler
    ) {
        name.text = habit.name
        priority.text = "Priority ${habit.priority}"
        description.text = habit.description

        itemView.setOnClickListener {
            clickHandler.handle(habit, position)
        }
    }
}