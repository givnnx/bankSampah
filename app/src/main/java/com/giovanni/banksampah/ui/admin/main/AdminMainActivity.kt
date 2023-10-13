package com.giovanni.banksampah.ui.admin.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.giovanni.banksampah.R
import com.giovanni.banksampah.databinding.AdminActivityMainBinding
import com.giovanni.banksampah.ui.admin.daftarpermintaan.DaftarPermintaanActivity
import com.giovanni.banksampah.ui.user.inputdata.InputDataActivity
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.giovanni.banksampah.ui.user.riwayat.RiwayatActivity

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: AdminActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        val userData = intent.getStringExtra(EXTRA_USER)
        binding.username.text = userData
    }

    private fun setNavigation() {
        binding.included.apply {
            cvDaftarPermintaan.setOnClickListener{
                val intent = Intent(this@AdminMainActivity, DaftarPermintaanActivity::class.java)
                startActivity(intent)
            }
            cvHistory.setOnClickListener{
                val intent = Intent(this@AdminMainActivity, RiwayatActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}