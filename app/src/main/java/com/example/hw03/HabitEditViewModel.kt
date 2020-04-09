package com.example.hw03

import androidx.lifecycle.ViewModel
import java.util.*

class HabitEditViewModel(

) : ViewModel() {
    var name: String = ""
    var description: String = ""
    var priority: Int = 1
    var type: String = "Good"
    var initialType: String = "Good"
    private var _repetitions: Int = 1
    var repetitions: String
        get() = _repetitions.toString()
        set(value) {
            _repetitions = value.toIntOrNull() ?: 0
        }
    private var _period: Int = 1
    var period: String
        get() = _period.toString()
        set(value) {
            _period = value.toIntOrNull() ?: 0
        }
    var id: UUID = UUID.randomUUID()

    fun initWith(habit: Habit): HabitEditViewModel {
        name = habit.name
        description = habit.description
        priority = habit.priority
        type = habit.type
        initialType = habit.type
        _repetitions = habit.repetitions
        _period = habit.period
        id = habit.id
        return this
    }

    fun save() {
        HabitsStorage.addOrUpdate(
            Habit(
                name,
                description,
                priority,
                type,
                _repetitions,
                _period,
                id
            )
        )
    }
}