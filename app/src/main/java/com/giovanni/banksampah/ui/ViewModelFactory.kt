package com.giovanni.banksampah.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.admin.daftarmintatariksaldo.DaftarPermintaanTarikSaldoViewModel
import com.giovanni.banksampah.ui.admin.daftarpermintaan.DaftarPermintaanViewModel
import com.giovanni.banksampah.ui.admin.riwayat.AdminRiwayatViewModel
import com.giovanni.banksampah.ui.user.riwayat.RiwayatViewModel

class ViewModelFactory constructor(private val mApplication: Application, private val pref: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RiwayatViewModel::class.java)) {
            return RiwayatViewModel(mApplication, pref) as T
        } else if (modelClass.isAssignableFrom(DaftarPermintaanViewModel::class.java)){
            return DaftarPermintaanViewModel(mApplication, pref) as T
        } else if (modelClass.isAssignableFrom(AdminRiwayatViewModel::class.java)){
            return AdminRiwayatViewModel(mApplication, pref) as T
        } else if (modelClass.isAssignableFrom(DaftarPermintaanTarikSaldoViewModel::class.java)){
            return DaftarPermintaanTarikSaldoViewModel(mApplication, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: UserPreference): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}