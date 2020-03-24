package com.example.hw03

import androidx.lifecycle.ViewModel
import java.util.*

class HabitsViewModel : ViewModel() {
    private val habits: MutableList<Habit> = mutableListOf(
    )

    fun getHabits(habitType: String): List<Habit> {
        return habits.filter { it.type == habitType }
    }

    fun addOrUpdate(newHabit: Habit) {
        val existingHabit = getIndexedHabit(newHabit.id)
        if (existingHabit != null) {
            habits[existingHabit.index] = newHabit
        } else {
            habits.add(newHabit)
        }
    }

    fun findById(id: UUID): Habit? {
        return getIndexedHabit(id)?.value
    }

    private fun getIndexedHabit(id: UUID) = habits.withIndex().find { it.value.id == id }
}