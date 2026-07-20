package com.example.healthtracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_activities")
data class UserActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val activityType: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)
