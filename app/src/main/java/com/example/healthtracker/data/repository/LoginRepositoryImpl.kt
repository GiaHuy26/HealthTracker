package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.domain.model.User
import com.example.healthtracker.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : LoginRepository {
    override suspend fun login(
        email: String,
        password: String
    ): User {
        val userEntity = userDao.getUserByEmail(email) ?: throw Exception("ERR_WRONG_CREDENTIALS")
        if (userEntity.password != password) {
            throw Exception("ERR_WRONG_CREDENTIALS")
        }
        val isProfileCompleted = !userEntity.userName.isNullOrBlank()
        return User(
            id = userEntity.id,
            email = userEntity.email,
            isProfileCompleted = isProfileCompleted
        )
    }
}