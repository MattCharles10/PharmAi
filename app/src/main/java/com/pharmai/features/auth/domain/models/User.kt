package com.pharmai.features.auth.domain.models

data class User(
    val id: String,
    val email: String,
    val name: String,
    val profileImageUrl: String? = null
)