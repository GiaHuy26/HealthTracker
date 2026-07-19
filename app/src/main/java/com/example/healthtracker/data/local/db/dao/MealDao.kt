package com.example.healthtracker.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthtracker.data.local.db.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM meals WHERE date = :date")
    fun getMealByDate(date: String): Flow<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity): Long

    @Delete
    suspend fun deleteMeal(log: MealEntity)

    @Query("SELECT * FROM meals WHERE date = :date AND mealType = :mealType")
    suspend fun getMealsByDateAndType(date: String, mealType: String): List<MealEntity>

    @Query("DELETE FROM meals WHERE date = :date AND mealType = :mealType")
    suspend fun deleteMealsByType(date: String, mealType: String)
}