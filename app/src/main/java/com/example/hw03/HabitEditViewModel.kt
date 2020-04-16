package com.example.hw03

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HabitEditViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val db = HabitsDatabase.getInstance(getApplication<Application>().applicationContext)
    private val habitsDao = db?.habitsDao()
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                habitsDao?.upsert(
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
    }
}