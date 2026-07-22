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
    @Query(
        "UPDATE meals SET foodName = :newName, servingSize = :newServing " +
            "WHERE userEmail = :userEmail AND foodName = :oldName"
    )
    suspend fun updateFoodLanguage(
        userEmail: String,
        oldName: String,
        newName: String,
        newServing: String
    )

    @Query("SELECT * FROM meals WHERE userEmail = :userEmail AND date = :date")
    fun getMealByDate(userEmail: String, date: String): Flow<List<MealEntity>>

    @Query(
        "SELECT * FROM meals WHERE userEmail = :userEmail " +
            "AND date BETWEEN :startDate AND :endDate ORDER BY date ASC"
    )
    fun getMealsBetweenDates(
        userEmail: String,
        startDate: String,
        endDate: String
    ): Flow<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity): Long

    @Delete
    suspend fun deleteMeal(log: MealEntity)

    @Query(
        "SELECT * FROM meals WHERE userEmail = :userEmail " +
            "AND date = :date AND mealType = :mealType"
    )
    suspend fun getMealsByDateAndType(
        userEmail: String,
        date: String,
        mealType: String
    ): List<MealEntity>

    @Query(
        "DELETE FROM meals WHERE userEmail = :userEmail " +
            "AND date = :date AND mealType = :mealType"
    )
    suspend fun deleteMealsByType(userEmail: String, date: String, mealType: String)
}
