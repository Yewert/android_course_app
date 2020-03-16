package com.example.hw03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HabitsListActivity : AppCompatActivity(), ISetActivityForResult {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var habits: MutableList<Habit>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habits_list)

        viewManager = LinearLayoutManager(this)

        habits = mutableListOf(Habit("hi", "not much", 7, "", 0, 0))
        viewAdapter = Adapter(habits, ItemClickHandler(this, this))

        recyclerView = findViewById<RecyclerView>(R.id.habits_list_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter

        }

        findViewById<FloatingActionButton>(R.id.habits_list_add).setOnClickListener {
            val intent = Intent(this, HabitEditActivity::class.java)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 1) {
            val habit = data!!.extractHabit()
            val index = data.getIntExtra("index", -1)
            if (index == -1) {
                habits.add(habit)
                viewAdapter.notifyItemInserted(habits.size - 1)
            } else {
                habits[index] = habit
                viewAdapter.notifyItemChanged(index)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun set(intent: Intent) {
        startActivityForResult(intent, 1)
    }
}
