package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovanni.banksampah.databinding.ActivityDaftarPermintaanTarikSaldoBinding
import com.giovanni.banksampah.model.TarikSaldoModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DaftarPermintaanTarikSaldoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarPermintaanTarikSaldoBinding
    private lateinit var viewModel:DaftarPermintaanTarikSaldoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarPermintaanTarikSaldoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel(this, dataStore)

        viewModel.getUser().observe(this) {
            if (it.level == "admin") {
                viewModel.fetchData()
            }
        }

        setToolbar()
        binding.rvDaftarPermintaanTarik.layoutManager = LinearLayoutManager(this)
        binding.rvDaftarPermintaanTarik.setHasFixedSize(true)

        viewModel.daftar.observe(this) {
            val mergedData = mutableListOf<TarikSaldoModel>()
            mergedData.addAll(it)
            viewModel.daftar.observe(this){
                setUsers(it)
            }
        }
    }

    private fun getViewModel (activity: AppCompatActivity, dataStore: DataStore<Preferences>): DaftarPermintaanTarikSaldoViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[DaftarPermintaanTarikSaldoViewModel::class.java]
    }

    private fun setUsers(items: List<TarikSaldoModel>){
        val adapter = DaftarPermintaanTarikSaldoAdapter(items, viewModel, this)
        binding.rvDaftarPermintaanTarik.adapter = adapter
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarMintaTarik)
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