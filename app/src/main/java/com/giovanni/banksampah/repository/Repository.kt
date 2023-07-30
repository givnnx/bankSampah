package com.giovanni.banksampah.repository

import android.app.Application
import com.giovanni.banksampah.database.BankSampahDao
import com.giovanni.banksampah.database.BankSampahDatabase
import com.giovanni.banksampah.model.Model
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val daoBS : BankSampahDao
    private val executor : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = BankSampahDatabase.getDatabase(application)
        daoBS = db.BankSampahDao()
    }

    fun insert(entity: Model){
        executor.execute{daoBS.insertData(entity)}
    }
}