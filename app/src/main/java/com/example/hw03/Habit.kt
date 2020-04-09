package com.example.hw03

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "habits")
@TypeConverters(UUIDConverter::class)
data class Habit(
    val name: String,
    val description: String,
    val priority: Int,
    val type: String,
    val repetitions: Int,
    val period: Int,
    @PrimaryKey
    val id: UUID = UUID.randomUUID()
) : Parcelable {
    companion object {
        val empty: Habit
            get() = Habit("name", "description", 1, "Good", 1, 1, UUID.randomUUID())
    }
}