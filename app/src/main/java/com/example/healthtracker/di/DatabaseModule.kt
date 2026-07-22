package com.example.healthtracker.di

import android.content.Context
import androidx.room.Room
import com.example.healthtracker.data.local.db.AppDatabase
import com.example.healthtracker.data.local.db.dao.FoodDao
import com.example.healthtracker.data.local.db.dao.MealDao
import com.example.healthtracker.data.local.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.healthtracker.data.local.db.dao.UserActivityDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "health_tracker_db"
        ).fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFoodDao(database: AppDatabase): FoodDao {
        return database.foodDao()
    }

    @Provides
    @Singleton
    fun provideMealDao(database: AppDatabase): MealDao {
        return database.mealDao()
    }

    @Provides
    @Singleton
    fun provideUserActivityDao(database: AppDatabase): UserActivityDao {
        return database.userActivityDao()
    }
}
