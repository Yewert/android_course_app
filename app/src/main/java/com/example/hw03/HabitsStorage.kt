package com.example.hw03

import android.database.Observable
import androidx.lifecycle.Observer
import java.util.*


object HabitsStorage : IHabitsStorageWrite, IHabitsStorageRead,
    Observable<Observer<IHabitsStorageRead>>() {
    private val map: MutableMap<UUID, Habit> = mutableMapOf()
    override fun addOrUpdate(habit: Habit) {
        map[habit.id] = habit
        mObservers.forEach { it.onChanged(this) }
    }

    override fun getHabits(): Sequence<Habit> {
        return map.asSequence().map { it.value }
    }
}

interface IHabitsStorageRead {
    fun getHabits(): Sequence<Habit>
}

interface IHabitsStorageWrite {
    fun addOrUpdate(habit: Habit)
}