package com.example.healthtracker.data.repository

import android.content.Context
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.dao.FoodDao
import com.example.healthtracker.data.local.db.dao.MealDao
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodDiaryRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val mealDao: MealDao
) : FoodDiaryRepository {
    override fun getMeal(date: String): Flow<List<MealEntity>> {
        return mealDao.getMealByDate(date)
    }

    override fun getMealsBetweenDates(
        startDate: String,
        endDate: String
    ): Flow<List<MealEntity>> {
        return mealDao.getMealsBetweenDates(startDate, endDate)
    }

    override suspend fun searchFoods(query: String): List<FoodEntity> {
        return foodDao.searchFoods(query)
    }

    override suspend fun addMeal(meal: MealEntity) {
        mealDao.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: MealEntity) {
        mealDao.deleteMeal(meal)
    }

    override suspend fun seedSampleFood(context: Context) {
        if (foodDao.getFoodCount() == 0) {
            val rawArray = context.resources.getStringArray(R.array.sample_foods_array)
            val foodToInsert = rawArray.mapNotNull { item ->
                val parts = item.split("|")
                if (parts.size == 3) {
                    FoodEntity(
                        name = parts[0],
                        calories = parts[1].toIntOrNull() ?: 100,
                        servingSize = parts[2]
                    )
                } else null
            }
            if (foodToInsert.isNotEmpty()) {
                foodDao.insertFood(foodToInsert)
            }
        }
    }

    override suspend fun getMealsByDateAndType(date: String, mealType: String): List<MealEntity> {
        return mealDao.getMealsByDateAndType(date, mealType)
    }

    override suspend fun deleteMealsByType(date: String, mealType: String) {
        mealDao.deleteMealsByType(date, mealType)
    }
}
