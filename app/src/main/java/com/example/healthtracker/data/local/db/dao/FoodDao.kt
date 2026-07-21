package com.example.healthtracker.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthtracker.data.local.db.entity.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%'")
    suspend fun searchFoods(query: String): List<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foods: List<FoodEntity>)

    @Query("DELETE FROM foods")
    suspend fun deleteAllFoods()
}
