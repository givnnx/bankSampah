package com.giovanni.banksampah.ui.user.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.databinding.ActivityMainBinding
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.user.inputdata.InputDataActivity
import com.giovanni.banksampah.ui.user.jenissampah.JenisSampahActivity
import com.giovanni.banksampah.ui.user.riwayat.RiwayatActivity
import com.giovanni.banksampah.ui.user.userprofile.UserProfileActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        getViewModel()
        viewModel.getUser().observe(this) {
            binding.username.text = it.username
        }

    }

    private fun getViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactoryMain(UserPreference.getInstance(dataStore)))[MainViewModel::class.java]
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
            cvKategori.setOnClickListener{
                val intent = Intent(this@MainActivity, JenisSampahActivity::class.java)
                startActivity(intent)
            }
        }
        binding.apply {
            imageProfile.setOnClickListener {
                val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}