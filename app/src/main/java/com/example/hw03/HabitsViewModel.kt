package com.example.hw03

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

typealias Filter = (Habit) -> Boolean

class HabitsViewModel(application: Application) : AndroidViewModel(application) {
    val habitsByType: MutableMap<String, MutableLiveData<List<Habit>>> = mutableMapOf(
        "Bad" to MutableLiveData(listOf()),
        "Good" to MutableLiveData(listOf())
    )
    private var allHabits = listOf<Habit>()
    private val db = HabitsDatabase.getInstance(getApplication<Application>().applicationContext)
    private val habitsDao = db?.habitsDao()


    private var filter: MutableLiveData<Filter?> = MutableLiveData(null)

    fun setFilter(filter: Filter?) {
        this.filter.value = filter
    }

    fun registerObserver(habitListObserver: IHabitsListObserver): LiveData<List<Habit>> {
        return MediatorLiveData<List<Habit>>().apply {
            addSource(habitsDao?.habits!!) {
                allHabits = it.toMutableList()
                filterByTypeAndCustomFilter(habitListObserver)
            }
            addSource(filter) {
                filterByTypeAndCustomFilter(habitListObserver)
            }
        }
    }

    private fun filterByTypeAndCustomFilter(habitListObserver: IHabitsListObserver) {
        habitsByType[habitListObserver.type]?.value = allHabits.filter { habit ->
            habit.type == habitListObserver.type && filter.value?.invoke(habit) ?: true
        }
    }
}