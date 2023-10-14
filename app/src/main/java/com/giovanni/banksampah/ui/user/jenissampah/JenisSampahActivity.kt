package com.giovanni.banksampah.ui.user.jenissampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.giovanni.banksampah.databinding.ActivityJenisSampahBinding

class JenisSampahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJenisSampahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}