package com.giovanni.banksampah.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.giovanni.banksampah.database.TarikSaldoDao
import com.giovanni.banksampah.database.TarikSaldoDatabase
import com.giovanni.banksampah.model.TarikSaldoModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TarikSaldoRepository(application: Application) {
    private val daoBS : TarikSaldoDao
    private val executor : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TarikSaldoDatabase.getDatabase(application)
        daoBS = db.TarikSaldoDao()
    }

    fun insert(entity: TarikSaldoModel){
        executor.execute{daoBS.insertData(entity)}
    }

    fun getData(): LiveData<List<TarikSaldoModel>> {
        return daoBS.getAll()
    }

    fun getDatabyStatus(status: String): LiveData<List<TarikSaldoModel>> {
        return daoBS.getDatabyStatus(status)
    }

    fun deleteData(uid: String){
        executor.execute{daoBS.deleteSingleData(uid)}
    }
}