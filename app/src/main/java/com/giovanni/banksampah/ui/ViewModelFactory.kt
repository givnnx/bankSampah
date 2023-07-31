package com.giovanni.banksampah.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.ui.user.inputdata.InputDataViewModel
import com.giovanni.banksampah.ui.user.riwayat.RiwayatViewModel

class ViewModelFactory constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputDataViewModel::class.java)) {
            return InputDataViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(RiwayatViewModel::class.java)) {
            return RiwayatViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

}