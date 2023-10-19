package com.giovanni.banksampah.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giovanni.banksampah.model.TarikSaldoModel

@Dao
interface TarikSaldoDao {
    @Query("SELECT * FROM db_saldo")
    fun getAll(): LiveData<List<TarikSaldoModel>>

    @Query("SELECT * FROM db_saldo WHERE status = :status")
    fun getDatabyStatus(status: String): LiveData<List<TarikSaldoModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(modelDatabases: TarikSaldoModel)

    @Query("DELETE FROM db_saldo WHERE uid= :uid")
    fun deleteSingleData(uid: String)
}