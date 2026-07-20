package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.domain.model.ActivityLevel
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.GoalType
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserProfileRepository {

    override suspend fun getProfile(email: String): Profile? {
        if (email.isBlank()) return null

        val user = userDao.getUserByEmail(email) ?: return null
        val userName = user.userName ?: return null
        val birthDate = user.birthDate ?: return null
        val gender = Gender.entries.firstOrNull { it.name == user.gender } ?: return null
        val weight = user.weight?.takeIf { it > 0f } ?: return null
        val height = user.height?.takeIf { it > 0f } ?: return null
        val activityLevel = ActivityLevel.entries.firstOrNull {
            it.name == user.activeLevel
        } ?: return null
        val goalType = GoalType.entries.firstOrNull {
            it.name == user.goalType
        } ?: return null

        return Profile(
            userName = userName,
            birthDate = birthDate,
            gender = gender,
            weight = weight,
            height = height,
            activeLevel = activityLevel,
            goalType = goalType
        )
    }

    override suspend fun getWeight(email: String): Float? {
        if (email.isBlank()) return null

        return userDao.getUserByEmail(email)
            ?.weight
            ?.takeIf { it > 0f }
    }
}
