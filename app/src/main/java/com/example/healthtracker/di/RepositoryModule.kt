package com.example.healthtracker.di

import com.example.healthtracker.data.repository.LoginRepositoryImpl
import com.example.healthtracker.data.repository.SignUpRepositoryImpl
import com.example.healthtracker.domain.repository.LoginRepository
import com.example.healthtracker.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSignUpRepository(
        signUpRepositoryImpl: SignUpRepositoryImpl
    ): SignUpRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository
}