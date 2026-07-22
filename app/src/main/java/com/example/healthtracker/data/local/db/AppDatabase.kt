package com.example.healthtracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthtracker.data.local.db.dao.FoodDao
import com.example.healthtracker.data.local.db.dao.MealDao
import com.example.healthtracker.data.local.db.dao.UserActivityDao
import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.data.local.db.entity.MealEntity
import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import com.example.healthtracker.data.local.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, FoodEntity::class, MealEntity::class, UserActivityEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    abstract fun mealDao(): MealDao
    abstract fun userActivityDao(): UserActivityDao
}
