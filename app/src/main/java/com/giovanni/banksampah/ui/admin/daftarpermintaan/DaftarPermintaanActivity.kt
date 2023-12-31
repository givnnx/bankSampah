package com.giovanni.banksampah.ui.admin.daftarpermintaan

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovanni.banksampah.databinding.ActivityDaftarPermintaanBinding
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DaftarPermintaanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDaftarPermintaanBinding
    private lateinit var viewModel:DaftarPermintaanViewModel
    lateinit var strKategori: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarPermintaanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        viewModel = getViewModel(this, dataStore)
        viewModel.getCategory()

        viewModel.getUser().observe(this) {
            if (it.level == "admin") {
                viewModel.Category.observe(this){
                    val updatedStrKategori = Array(it.size) { "" }

                    for ((index, category) in it.withIndex()) {
                        updatedStrKategori[index] = category.jenis
                    }
                    strKategori = updatedStrKategori

                    for (item in strKategori) {
                        viewModel.fetchData(item)
                    }
                }
            }
        }

        binding.rvDaftarPermintaan.layoutManager = LinearLayoutManager(this)
        binding.rvDaftarPermintaan.setHasFixedSize(true)
        viewModel.daftar3.observe(this){
            setUsers(it)
        }
    }

    private fun getViewModel (activity: AppCompatActivity, dataStore: DataStore<Preferences>): DaftarPermintaanViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[DaftarPermintaanViewModel::class.java]
    }
    private fun setUsers(items: List<Model>){
        val adapter = DaftarPermintaanAdapter(items, viewModel, this)
        binding.rvDaftarPermintaan.adapter = adapter
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
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