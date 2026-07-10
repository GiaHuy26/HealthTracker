package com.example.healthtracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.healthtracker.domain.model.Gender

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    val userName: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val weight: Float? = null,
    val height: Float? = null,
    val activeLevel: String? = null,
    val goalType: String? = null
)