package com.example.healthtracker.domain.validation

private val emailPattern = Regex(
    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
)

fun normalizeEmail(email: String): String {
    return email.trim().lowercase()
}

fun isValidEmail(email: String): Boolean {
    return emailPattern.matches(email)
}
