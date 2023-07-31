package com.giovanni.banksampah.ui.user.inputdata

import android.app.Application
import androidx.lifecycle.ViewModel
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InputDataViewModel(application: Application): ViewModel() {
    private val repository: Repository = Repository(application)
    fun addOrder(nama: String, kategori: String, berat: Int, harga: Int, tanggal: String, alamat:String, catatan: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Model(
                namaPengguna = nama,
                jenisSampah = kategori,
                berat = berat,
                harga = harga,
                tanggal = tanggal,
                alamat = alamat,
                catatan = catatan
            )
            repository.insert(user)
        }
    }
}