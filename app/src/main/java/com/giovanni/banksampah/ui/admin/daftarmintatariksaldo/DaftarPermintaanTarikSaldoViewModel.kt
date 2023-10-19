package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.TarikSaldoRepository
import com.google.firebase.firestore.FirebaseFirestore

class DaftarPermintaanTarikSaldoViewModel(application: Application, private val pref:UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: TarikSaldoRepository = TarikSaldoRepository(application)
    private val _state = MutableLiveData<Int>()

    val state: LiveData<Int>
        get() = _state

    val daftar = repository.getDatabyStatus("Belum diterima")
    val daftar2 = repository.getDatabyStatus("Diproses")

    fun updateState(newState: Int) {
        _state.value = newState
    }

    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }
}