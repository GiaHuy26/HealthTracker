package com.example.healthtracker.domain.repository

import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import kotlinx.coroutines.flow.Flow

interface ActivityDiaryRepository {
    fun getActivitiesByDate(
        userEmail: String,
        date: String
    ): Flow<List<UserActivityEntity>>
    fun getActivitiesBetweenDates(
        userEmail: String,
        startDate: String,
        endDate: String
    ): Flow<List<UserActivityEntity>>
    suspend fun addActivity(activity: UserActivityEntity)
    suspend fun deleteActivity(activity: UserActivityEntity)
}
