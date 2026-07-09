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
        userName: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                return Result.failure(Exception(""))
            }
            val newUserEntity = UserEntity(
                email = email,
                password = password,
                name = userName
            )
            val generatedId = userDao.insertUser(newUserEntity)
            Result.success(
                User(id = generatedId.toInt(), email = email, name = userName)
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}