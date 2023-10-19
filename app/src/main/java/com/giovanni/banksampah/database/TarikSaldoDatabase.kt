package com.giovanni.banksampah.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giovanni.banksampah.model.TarikSaldoModel

@Database(
    entities = [TarikSaldoModel::class],
    version = 1
)
abstract class TarikSaldoDatabase: RoomDatabase() {
    abstract fun TarikSaldoDao(): TarikSaldoDao

    companion object {
        @Volatile
        private var INSTANCE: TarikSaldoDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TarikSaldoDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TarikSaldoDatabase::class.java, "db_saldo"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}