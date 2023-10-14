package com.giovanni.banksampah.ui.user.tariksaldo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.databinding.ActivityTarikSaldoBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.user.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class TarikSaldoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTarikSaldoBinding
    private lateinit var viewModel: TarikSaldoViewModel
    private var saldo:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarikSaldoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getViewModel()
        setAction()
        setToolbar()
    }

    private fun getViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryTarikSaldo(UserPreference.getInstance(dataStore)))[TarikSaldoViewModel::class.java]
    }

    private fun setAction() {
        binding.btnTarik.setOnClickListener {
            viewModel.getUser().observe(this){
                saldo = it.saldo
                if(binding.inputPenarikan.text.toString().toLong() > saldo){
                    Toast.makeText(this, "Saldo anda kurang!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Menunggu konfirmasi, silahkan tunggu!", Toast.LENGTH_SHORT).show()
                    finishAffinity()
                    val logout = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(logout)
                    finish()
                }
            }
        }
        viewModel.getUser().observe(this) {
            Log.d("Saldo", it.saldo.toString())
            binding.tvSaldoTarik.text = Helper.rupiahFormat(it.saldo.toInt())
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarTariktunai)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}