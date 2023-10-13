package com.giovanni.banksampah.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giovanni.banksampah.model.Model

@Dao
interface BankSampahDao {
    @Query("SELECT * FROM db_banksampah")
    fun getAll(): LiveData<List<Model>>
    @Query("SELECT * FROM db_banksampah WHERE status = :status")
    fun getDatabyStatus(status: String): LiveData<List<Model>>

    @Query("SELECT SUM(harga) FROM db_banksampah")
    fun getSaldo(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(modelDatabases: Model)

    @Query("DELETE FROM db_banksampah WHERE uid= :uid")
    fun deleteSingleData(uid: String)
}