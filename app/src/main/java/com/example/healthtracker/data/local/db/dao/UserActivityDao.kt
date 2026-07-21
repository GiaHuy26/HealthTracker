package com.example.healthtracker.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserActivityDao {

    @Query("SELECT * FROM user_activities WHERE date = :date")
    fun getActivitiesByDate(date: String): Flow<List<UserActivityEntity>>

    @Query("SELECT * FROM user_activities WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getActivitiesBetweenDates(
        startDate: String,
        endDate: String
    ): Flow<List<UserActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: UserActivityEntity): Long

    @Delete
    suspend fun deleteActivity(activity: UserActivityEntity)

    @Query("DELETE FROM user_activities WHERE date = :date")
    suspend fun clearActivitiesByDate(date: String)
}
