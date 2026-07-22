package com.example.healthtracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_activities",
    indices = [Index(value = ["userEmail", "date"])]
)
data class UserActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val date: String,
    val activityType: String,
    val durationMinutes: Int,
    val caloriesBurned: Int
)
