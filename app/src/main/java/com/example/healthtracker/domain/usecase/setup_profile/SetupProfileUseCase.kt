package com.example.healthtracker.domain.usecase.setup_profile

import com.example.healthtracker.domain.model.Profile
import com.example.healthtracker.domain.repository.SetupProfileRepository
import javax.inject.Inject

class SetupProfileUseCase @Inject constructor(
    private val profileRepository: SetupProfileRepository
) {
    suspend operator fun invoke(
        email: String,
        profile: Profile
    ) {
        if (profile.userName.isBlank()) {
            throw Exception("ERR_NAME_EMPTY")
        }
        if (profile.birthDate.isBlank()) {
            throw Exception("ERR_BIRTHDAY_EMPTY")
        }
        if (profile.weight <= 0f) {
            throw Exception("ERR_WEIGHT_INVALID")
        }
        if (profile.height <= 0f) {
            throw Exception("ERR_HEIGHT_INVALID")
        }
        profileRepository.saveProfile(email, profile)
    }
}