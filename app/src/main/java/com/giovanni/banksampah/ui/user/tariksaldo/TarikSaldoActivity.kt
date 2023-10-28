package com.giovanni.banksampah.ui.user.tariksaldo

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.databinding.ActivityTarikSaldoBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.UserPreference

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
        viewModel.isLoading.observe(this){
            showLoading(it)
        }
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
                if(binding.inputPenarikan.text.toString().toLong() > saldo || saldo == 0.toLong()){
                    Toast.makeText(this, "Saldo anda kurang!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Menunggu konfirmasi, silahkan tunggu!", Toast.LENGTH_SHORT).show()
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val formattedDate = "$day-$month-$year"
                    val jumlah = binding.inputPenarikan.text.toString().toLong()
                    viewModel.sendRequest(jumlah, it.saldo, "Belum diterima", formattedDate, it.username, this, it.uid)
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

    private fun showLoading(isLoading: Boolean){
        binding.apply {
            if (isLoading) {
                pbSignin.visibility = View.VISIBLE
                overlayView.visibility = View.VISIBLE
                btnTarik.isEnabled = false
            } else {
                pbSignin.visibility = View.GONE
                overlayView.visibility = View.GONE
                btnTarik.isEnabled = true
            }
        }
    }
}