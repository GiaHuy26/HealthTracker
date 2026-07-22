package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.db.dao.UserActivityDao
import com.example.healthtracker.data.local.db.entity.UserActivityEntity
import com.example.healthtracker.domain.repository.ActivityDiaryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ActivityDiaryRepositoryImpl @Inject constructor(
    private val userActivityDao: UserActivityDao
) : ActivityDiaryRepository {

    override fun getActivitiesByDate(
        userEmail: String,
        date: String
    ): Flow<List<UserActivityEntity>> {
        return userActivityDao.getActivitiesByDate(userEmail, date)
    }

    override fun getActivitiesBetweenDates(
        userEmail: String,
        startDate: String,
        endDate: String
    ): Flow<List<UserActivityEntity>> {
        return userActivityDao.getActivitiesBetweenDates(userEmail, startDate, endDate)
    }

    override suspend fun addActivity(activity: UserActivityEntity) {
        userActivityDao.insertActivity(activity)
    }

    override suspend fun deleteActivity(activity: UserActivityEntity) {
        userActivityDao.deleteActivity(activity)
    }
}
