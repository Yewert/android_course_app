package com.example.hw03

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private val habits: List<Habit>,
    private val clickHandler: ItemClickHandler
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.habit_view, parent, false))
    }
    override fun getItemCount(): Int = habits.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position], position, clickHandler)
    }
}

class ItemClickHandler(private val context: Context, private val setter: ISetActivityForResult) {
    fun handle(habit: Habit, position: Int) {
        val intent = Intent(context, HabitEditActivity::class.java).apply {
            val bundle = Bundle().apply { putInt("index", position) }
            habit.applyToBundle(bundle)
            putExtras(bundle)
        }
        setter.set(intent)
    }
}

interface ISetActivityForResult {
    fun set(intent: Intent)
}