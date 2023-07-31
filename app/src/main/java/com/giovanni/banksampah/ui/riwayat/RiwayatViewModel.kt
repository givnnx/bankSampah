package com.giovanni.banksampah.ui.riwayat

import android.app.Application
import androidx.lifecycle.ViewModel
import com.giovanni.banksampah.repository.Repository

class RiwayatViewModel(application: Application): ViewModel() {
    private val repository: Repository = Repository(application)

    val riwayat = repository.getData()
}