package com.example.healthtracker.domain.repository

import android.content.Context
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.data.local.db.entity.MealEntity
import kotlinx.coroutines.flow.Flow

interface FoodDiaryRepository {
    fun getMeal(date:String): Flow<List<MealEntity>>
    suspend fun searchFoods(query:String): List<FoodEntity>
    suspend fun addMeal(meal: MealEntity)
    suspend fun deleteMeal(meal: MealEntity)
    suspend fun seedSampleFood(context: Context)
}