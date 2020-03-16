package com.example.hw03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class HabitEditActivity : AppCompatActivity() {
    private var habitIndex: Int = -1
    private lateinit var habitName: EditText
    private lateinit var habitDescription: EditText
    private lateinit var habitPriority: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_edit)
        habitName = findViewById(R.id.habit_name_edit)
        habitDescription = findViewById(R.id.habit_description_edit)
        habitPriority = findViewById(R.id.habit_priority_edit)

        habitIndex = intent.getIntExtra("index", -1);
        val habitToEdit = intent.extractHabit()
        habitName.setText(habitToEdit.name)
        habitDescription.setText(habitToEdit.description)
        habitPriority.progress = habitToEdit.priority

        findViewById<Button>(R.id.habit_edit_save).setOnClickListener {
            val datax = Intent(this, HabitsListActivity::class.java).apply {
                val bundle = Bundle().apply { putInt("index", habitIndex) }
                val habit = Habit(
                    habitName.text.toString(),
                    habitDescription.text.toString(),
                    habitPriority.progress,
                    "",
                    0,
                    0
                )
                habit.applyToBundle(bundle)
                putExtras(bundle)
            }
            setResult(1, datax)
            finish()
        }
    }
}
