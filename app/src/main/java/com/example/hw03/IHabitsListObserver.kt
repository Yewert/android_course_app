package com.example.hw03

import java.util.*

interface IHabitsListObserver {
    val type: String

    fun onHabitChanged(id: UUID)
    fun onHabitRemoved(id: UUID)
    fun reload()
}