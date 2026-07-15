package com.example.healthtracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val mealType: String,
    val foodName: String,
    val calories: Int,
    val servingSize: String,
    val quantity: Float
)