package com.giovanni.banksampah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_saldo")
data class TarikSaldoModel(
    @PrimaryKey
    val uid: String,
    @ColumnInfo
    val username: String,
    @ColumnInfo
    val jumlah: Long,
    @ColumnInfo
    var status: String,
    @ColumnInfo
    val tanggal: String,
    @ColumnInfo
    val saldo: Long,
    @ColumnInfo
    val idPengguna: String
)
