package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.SetupProfileRepository
import javax.inject.Inject

class SetupProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : SetupProfileRepository {
    override suspend fun saveProfile(
        email: String,
        profile: Profile
    ) {
        val userEntity = userDao.getUserByEmail(email) ?: throw Exception("ERR_USER_NOT_FOUND")

        val updateUser = userEntity.copy(
            userName = profile.userName,
            gender = profile.gender.name,
            birthDate = profile.birthDate,
            weight = profile.weight,
            height = profile.height,
            activeLevel = profile.activeLevel.name,
            goalType = profile.goalType.name
        )

        userDao.updateUser(updateUser)
    }

}