package com.giovanni.banksampah.ui.user.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.giovanni.banksampah.databinding.ActivityMainBinding
import com.giovanni.banksampah.ui.user.inputdata.InputDataActivity
import com.giovanni.banksampah.ui.user.riwayat.RiwayatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()
        val userData = intent.getStringExtra(EXTRA_USER)
        binding.username.text = userData
    }

    private fun setNavigation() {
        binding.included.apply {
            cvInput.setOnClickListener{
                val intent = Intent(this@MainActivity, InputDataActivity::class.java)
                startActivity(intent)
            }
            cvHistory.setOnClickListener{
                val intent = Intent(this@MainActivity, RiwayatActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}