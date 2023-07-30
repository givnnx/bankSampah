package com.giovanni.banksampah.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giovanni.banksampah.model.Model

@Database(
    entities =[Model::class],
    version = 1
)
abstract class BankSampahDatabase: RoomDatabase() {
    abstract fun BankSampahDao(): BankSampahDao

    companion object {
        @Volatile
        private var INSTANCE: BankSampahDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): BankSampahDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BankSampahDatabase::class.java, "db_banksampah"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}