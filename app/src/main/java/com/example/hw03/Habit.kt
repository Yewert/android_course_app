package com.example.hw03

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Habit(
    val name: String,
    val description: String,
    val priority: Int,
    val type: String,
    val repetitions: Int,
    val period: Int,
    val id: UUID = UUID.randomUUID()
) : Parcelable {
    companion object {
        val empty: Habit
            get() = Habit("name", "description", 1, "Good", 1, 1, UUID.randomUUID())
    }
}