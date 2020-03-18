package com.example.hw03

import android.content.Intent
import android.os.Bundle

data class Habit(
    val name: String,
    val description: String,
    val priority: Int,
    val type: String,
    val repetitions: Int,
    val period: Int
)

fun Habit.applyToBundle(bundle: Bundle) {
    bundle.apply {
        putString("name", name)
        putString("description", description)
        putString("type", type)
        putInt("priority", priority)
        putInt("repetitions", repetitions)
        putInt("period", period)
    }
}

fun Intent.extractHabit(): Habit {
    return Habit(
        getStringExtra("name") ?: "",
        getStringExtra("description") ?: "",
        getIntExtra("priority", 1),
        getStringExtra("type") ?: "Food",
        getIntExtra("repetitions", 1),
        getIntExtra("period", 1)
    )
}