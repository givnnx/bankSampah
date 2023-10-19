package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.giovanni.banksampah.R
import com.giovanni.banksampah.databinding.ActivityDaftarPermintaanTarikSaldoBinding
import com.giovanni.banksampah.model.Model

class DaftarPermintaanTarikSaldoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarPermintaanTarikSaldoBinding
    private lateinit var viewModel:DaftarPermintaanTarikSaldoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_permintaan_tarik_saldo)
    }

    private fun setUsers(items: List<Model>){
        val adapter = DaftarPermintaanTarikSaldoAdapter(items, viewModel, this)
        binding.rvDaftarPermintaanTarik.adapter = adapter
    }

}