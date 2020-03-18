package com.example.hw03

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HabitEditActivity : AppCompatActivity() {
    private var habitIndex: Int = -1
    private lateinit var habitName: EditText
    private lateinit var habitDescription: EditText
    private lateinit var habitRepetitions: EditText
    private lateinit var habitPeriod: EditText
    private lateinit var habitType: RadioGroup
    private lateinit var habitPriority: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_edit)
        habitName = findViewById(R.id.habit_name_edit)
        habitDescription = findViewById(R.id.habit_description_edit)
        habitRepetitions = findViewById(R.id.habit_repetitions_edit)
        habitPeriod = findViewById(R.id.habit_period_edit)
        habitType = findViewById(R.id.habit_type_edit)
        habitPriority = findViewById(R.id.habit_priority_edit)

        habitIndex = intent.getIntExtra("index", -1);
        val habitToEdit = intent.extractHabit()
        habitName.setText(habitToEdit.name)
        habitDescription.setText(habitToEdit.description)
        habitPriority.progress = habitToEdit.priority
        habitType.check(typeToId(habitToEdit.type) ?: R.id.habit_type_selector_1)
        habitRepetitions.setText(habitToEdit.repetitions.toString())
        habitPeriod.setText(habitToEdit.period.toString())

        findViewById<Button>(R.id.habit_edit_save).setOnClickListener {
            val datax = Intent(this, HabitsListActivity::class.java).apply {
                val bundle = Bundle().apply { putInt("index", habitIndex) }
                val habit = Habit(
                    habitName.text.toString(),
                    habitDescription.text.toString(),
                    habitPriority.progress,
                    findViewById<RadioButton>(habitType.checkedRadioButtonId).text.toString(),
                    habitRepetitions.text.toString().toIntOrNull() ?: 0,
                    habitPeriod.text.toString().toIntOrNull() ?: 0
                )
                habit.applyToBundle(bundle)
                putExtras(bundle)
            }
            setResult(1, datax)
            finish()
        }
    }

    private fun typeToId(type: String): Int? {
        return when (type) {
            "Food" -> R.id.habit_type_selector_1
            "Activity" -> R.id.habit_type_selector_2
            "Task" -> R.id.habit_type_selector_3
            else -> null
        }
    }
}
