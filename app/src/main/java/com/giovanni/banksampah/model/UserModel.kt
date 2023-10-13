package com.giovanni.banksampah.model

data class UserModel(
    val uid: String,
    val username: String,
    val email: String,
    val level: String,
    val alamat: String,
    val saldo: Long,
    val loginState: Boolean
) {
    constructor() : this("", "", "", "", "", 0 ,  false)
}