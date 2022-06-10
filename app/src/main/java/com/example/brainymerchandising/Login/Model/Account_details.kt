package com.example.brainymerchandising.Login.Model

data class Account_details(
    val createdAt: String,
    val email: String,
    val enabled: Boolean,
    val first_name: String,
    val gender: String,
    val id: Int,
    val last_name: String,
    val password: String,
    val phone_number: String,
    val profile_picture: String,
    val role: User_Role,
    val roleId: Int,
    val updatedAt: String,
    val username: String


)
