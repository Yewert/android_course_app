package com.example.hw03

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class HabitEditFragment : Fragment() {
    private lateinit var habitId: UUID
    private lateinit var habitOriginalType: String
    private lateinit var habitName: EditText
    private lateinit var habitDescription: EditText
    private lateinit var habitRepetitions: EditText
    private lateinit var habitPeriod: EditText
    private lateinit var habitType: RadioGroup
    private lateinit var habitPriority: SeekBar

    private lateinit var saveHandler: ISaveHabitHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveHandler = requireActivity() as ISaveHabitHandler
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (requireActivity() as IDrawerLocker).setDrawerEnabled(false)

        habitName = view.findViewById(R.id.habit_name_edit)
        habitDescription = view.findViewById(R.id.habit_description_edit)
        habitRepetitions = view.findViewById(R.id.habit_repetitions_edit)
        habitPeriod = view.findViewById(R.id.habit_period_edit)
        habitType = view.findViewById(R.id.habit_type_edit)
        habitPriority = view.findViewById(R.id.habit_priority_edit)

        val habitToEdit = arguments?.getParcelable("habit") ?: Habit.default
        habitId = habitToEdit.id
        habitOriginalType = habitToEdit.type
        habitName.setText(habitToEdit.name)
        habitDescription.setText(habitToEdit.description)
        habitPriority.progress = habitToEdit.priority
        habitType.check(typeToId(habitToEdit.type) ?: R.id.habit_type_selector_1)
        habitRepetitions.setText(habitToEdit.repetitions.toString())
        habitPeriod.setText(habitToEdit.period.toString())


        view.findViewById<Button>(R.id.habit_edit_save).setOnClickListener {
            if (isValid()) {
                val habit = Habit(
                    habitName.text.toString(),
                    habitDescription.text.toString(),
                    habitPriority.progress,
                    view.findViewById<RadioButton>(habitType.checkedRadioButtonId).text.toString(),
                    habitRepetitions.text.toString().toIntOrNull() ?: 0,
                    habitPeriod.text.toString().toIntOrNull() ?: 0,
                    habitId
                )

                saveHandler.handleSave(habit, habitOriginalType)
            }
        }
    }

    private fun typeToId(type: String): Int? {
        return when (type) {
            "Good" -> R.id.habit_type_selector_1
            "Bad" -> R.id.habit_type_selector_2
            else -> null
        }
    }

    private fun isValid(): Boolean {
        var valid = true
        if (habitName.text.isBlank()) {
            habitName.error = "invalid"
            valid = false
        }
        if (habitDescription.text.isBlank()) {
            habitDescription.error = "invalid"
            valid = false
        }

        return valid
    }

    public override fun onDestroy() {
        super.onDestroy()

        (requireActivity() as IDrawerLocker).setDrawerEnabled(true)
    }

    companion object {
        fun newInstance(habit: Habit) = HabitEditFragment().apply {
            arguments = Bundle().apply { putParcelable("habit", habit) }
        }
    }
}
