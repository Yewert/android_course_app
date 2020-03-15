package com.example.hw03

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.habit_name)
    fun bind(habit: Habit) {
        name.text = habit.name
    }
}