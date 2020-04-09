package com.example.hw03

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
@TypeConverters(UUIDConverter::class)
interface HabitsDao {
    @get:Query("SELECT * FROM habits")
    val habits: LiveData<List<Habit>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(habit: Habit): Long

    @Update
    fun update(habit: Habit)

    @Transaction
    fun upsert(habit: Habit) {
        val id: Long = insert(habit)
        if (id == -1L) {
            update(habit)
        }
    }
}