package com.giovanni.banksampah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_banksampah")
data class Model(
    @PrimaryKey
    var uid: String,

    @ColumnInfo(name = "nama_pengguna")
    var namaPengguna: String,

    @ColumnInfo(name = "jenis_sampah")
    var jenisSampah: String,

    @ColumnInfo(name = "berat")
    var berat: Int = 0,

    @ColumnInfo(name = "harga")
    var harga: Int = 0,

    @ColumnInfo(name = "tanggal")
    var tanggal: String,

    @ColumnInfo(name = "alamat")
    var alamat: String,

    @ColumnInfo(name = "catatan")
    var catatan: String
)
