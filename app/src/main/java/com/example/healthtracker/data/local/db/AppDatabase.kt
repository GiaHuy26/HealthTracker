package com.example.healthtracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.data.local.db.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}