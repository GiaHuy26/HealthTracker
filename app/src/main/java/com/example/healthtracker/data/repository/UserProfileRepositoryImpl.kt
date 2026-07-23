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
        val userName = user.userName
        val birthDate = user.birthDate
        val genderName = user.gender
        val weight = user.weight
        val height = user.height
        val activityLevelName = user.activeLevel
        val goalTypeName = user.goalType

        if (userName == null || birthDate == null) {
            return null
        }
        if (genderName == null || activityLevelName == null || goalTypeName == null) {
            return null
        }
        if (weight == null || weight <= 0f) {
            return null
        }
        if (height == null || height <= 0f) {
            return null
        }

        val gender = Gender.entries.firstOrNull { gender ->
            gender.name == genderName
        }
        if (gender == null) {
            return null
        }

        val activityLevel = ActivityLevel.entries.firstOrNull {
            it.name == activityLevelName
        }
        if (activityLevel == null) {
            return null
        }

        val goalType = GoalType.entries.firstOrNull {
            it.name == goalTypeName
        }
        if (goalType == null) {
            return null
        }

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

        val user = userDao.getUserByEmail(email) ?: return null
        val weight = user.weight ?: return null

        if (weight <= 0f) {
            return null
        }

        return weight
    }
}
