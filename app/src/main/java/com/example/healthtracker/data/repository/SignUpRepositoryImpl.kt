package com.example.healthtracker.data.repository

import com.example.healthtracker.data.local.db.dao.UserDao
import com.example.healthtracker.data.local.db.entity.UserEntity
import com.example.healthtracker.domain.model.User
import com.example.healthtracker.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : SignUpRepository {
    override suspend fun signUp(
        email: String,
        password: String
    ): User {
        val existUser = userDao.getUserByEmail(email)
        if (existUser != null) {
            throw Exception("")
        }

        val newUserEntity = UserEntity(
            email = email,
            password = password
        )

        val generatedId = userDao.insertUser(newUserEntity)

        return User(id = generatedId.toInt(), email = email)
    }
}