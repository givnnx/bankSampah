package com.giovanni.banksampah.model

data class UserModel(
    val uid: String,
    val username: String,
    val email: String,
    val password: String,
    val level: String
) {
    constructor() : this("", "", "", "", "")
}
