package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.databinding.ActivityDaftarPermintaanTarikSaldoBinding
import com.giovanni.banksampah.model.Model
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
    }

    private fun getViewModel (activity: AppCompatActivity, dataStore: DataStore<Preferences>): DaftarPermintaanTarikSaldoViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[DaftarPermintaanTarikSaldoViewModel::class.java]
    }

    private fun setUsers(items: List<Model>){
        val adapter = DaftarPermintaanTarikSaldoAdapter(items, viewModel, this)
        binding.rvDaftarPermintaanTarik.adapter = adapter
    }

}