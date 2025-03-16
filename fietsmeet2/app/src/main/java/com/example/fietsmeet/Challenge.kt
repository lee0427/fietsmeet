package com.example.fietsmeet

data class Challenge(
    val title: String,
    val description: String,
    val progress: Int, // Progress percentage (0-100)
    val timeLeft: String, // Time left for challenge
//    val profileImageResId: Int // Profile image of the challenge
)
