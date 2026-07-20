package com.example.healthtracker.di

import com.example.healthtracker.data.repository.ActivityDiaryRepositoryImpl
import com.example.healthtracker.data.repository.FoodDiaryRepositoryImpl
import com.example.healthtracker.data.repository.LoginRepositoryImpl
import com.example.healthtracker.data.repository.SessionManagerImpl
import com.example.healthtracker.data.repository.SetupProfileRepositoryImpl
import com.example.healthtracker.data.repository.SignUpRepositoryImpl
import com.example.healthtracker.data.repository.UserProfileRepositoryImpl
import com.example.healthtracker.domain.repository.ActivityDiaryRepository
import com.example.healthtracker.domain.repository.FoodDiaryRepository
import com.example.healthtracker.domain.repository.LoginRepository
import com.example.healthtracker.domain.repository.SetupProfileRepository
import com.example.healthtracker.domain.repository.SignUpRepository
import com.example.healthtracker.domain.repository.UserProfileRepository
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

    @Binds
    @Singleton
    abstract fun bindSetupProfileRepository(
        setupProfileRepositoryImpl: SetupProfileRepositoryImpl
    ): SetupProfileRepository

    @Binds
    @Singleton
    abstract fun binSessionManager(
        sessionManagerImpl: SessionManagerImpl
    ): SessionManager

    @Binds
    @Singleton
    abstract fun binFoodDiaryRepository(
        foodDiaryRepositoryImpl: FoodDiaryRepositoryImpl
    ): FoodDiaryRepository

    @Binds
    @Singleton
    abstract fun bindActivityDiaryRepository(
        activityDiaryRepositoryImpl: ActivityDiaryRepositoryImpl
    ): ActivityDiaryRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl
    ): UserProfileRepository
}
