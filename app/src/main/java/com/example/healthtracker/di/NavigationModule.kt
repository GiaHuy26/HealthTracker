package com.example.healthtracker.di

import com.example.healthtracker.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun providerNavigationManager(): NavigationManager {
        return NavigationManager()
    }
}