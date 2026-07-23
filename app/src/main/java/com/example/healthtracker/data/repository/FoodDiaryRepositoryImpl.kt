package com.example.healthtracker.data.repository

import android.content.Context
import android.content.res.Configuration
import com.example.healthtracker.R
import com.example.healthtracker.data.local.db.dao.FoodDao
import com.example.healthtracker.data.local.db.dao.MealDao
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Locale

@Singleton
class FoodDiaryRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val mealDao: MealDao
) : FoodDiaryRepository {
    override fun getMeal(userEmail: String, date: String): Flow<List<MealEntity>> {
        return mealDao.getMealByDate(userEmail, date)
    }

    override fun getMealsBetweenDates(
        userEmail: String,
        startDate: String,
        endDate: String
    ): Flow<List<MealEntity>> {
        return mealDao.getMealsBetweenDates(userEmail, startDate, endDate)
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

    override suspend fun seedSampleFood(
        context: Context,
        languageCode: String,
        userEmail: String
    ) {
        val foodsToInsert = getSampleFoods(context, languageCode)
        val oldLanguageCode = if (languageCode == "vi") "en" else "vi"
        val oldFoods = getSampleFoods(context, oldLanguageCode)

        val numberOfFoodsToUpdate = minOf(oldFoods.size, foodsToInsert.size)

        for (index in 0 until numberOfFoodsToUpdate) {
            val oldFood = oldFoods[index]
            val newFood = foodsToInsert[index]

            mealDao.updateFoodLanguage(
                userEmail = userEmail,
                oldName = oldFood.name,
                newName = newFood.name,
                newServing = newFood.servingSize
            )
        }

        if (foodsToInsert.isNotEmpty()) {
            foodDao.deleteAllFoods()
            foodDao.insertFood(foodsToInsert)
        }
    }

    private fun getSampleFoods(
        context: Context,
        languageCode: String
    ): List<FoodEntity> {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale.forLanguageTag(languageCode))
        val localizedContext = context.createConfigurationContext(configuration)
        val rawArray = localizedContext.resources.getStringArray(R.array.sample_foods_array)
        val foods = mutableListOf<FoodEntity>()

        for (item in rawArray) {
            val parts = item.split("|")
            if (parts.size != 3) {
                continue
            }

            val calories = parts[1].toIntOrNull() ?: 100
            val food = FoodEntity(
                name = parts[0],
                calories = calories,
                servingSize = parts[2]
            )
            foods.add(food)
        }

        return foods
    }

    override suspend fun getMealsByDateAndType(
        userEmail: String,
        date: String,
        mealType: String
    ): List<MealEntity> {
        return mealDao.getMealsByDateAndType(userEmail, date, mealType)
    }

    override suspend fun deleteMealsByType(
        userEmail: String,
        date: String,
        mealType: String
    ) {
        mealDao.deleteMealsByType(userEmail, date, mealType)
    }
}
