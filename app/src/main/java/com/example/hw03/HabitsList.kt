package com.example.hw03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HabitsList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habits_list)

        viewManager = LinearLayoutManager(this)

        val habits = listOf(
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("smoking"),
            Habit("huh")
        )

        viewAdapter = Adapter(habits)

        recyclerView = findViewById<RecyclerView>(R.id.habits_list_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
}
