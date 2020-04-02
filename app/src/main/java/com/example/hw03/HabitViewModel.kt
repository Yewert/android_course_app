package com.example.hw03

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import java.util.*

typealias Filter = (Habit) -> Boolean

class HabitsViewModel : ViewModel(), Observer<IHabitsStorageRead> {
    private lateinit var habits: List<Habit>
    private lateinit var storage: IHabitsStorageRead

    fun initWithStorage(storage: IHabitsStorageRead) {
        this.storage = storage
        onChanged(storage)
    }

    fun getHabits(habitType: String): List<Habit> {
        return habits.filter { it.type == habitType }
    }


    fun findById(id: UUID): Habit? {
        return getIndexedHabit(id)?.value
    }

    private fun getIndexedHabit(id: UUID) = habits.withIndex().find { it.value.id == id }

    private var filter: Filter? = null
    val isFilterEnabled: Boolean
        get() = filter != null

    fun setFilter(filter: Filter?) {
        this.filter = filter
        onChanged(storage)
    }

    override fun onChanged(t: IHabitsStorageRead) {
        habits = t.getHabits().filter { filter?.invoke(it) ?: true }.toList()
    }
}