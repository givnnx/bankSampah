package com.giovanni.banksampah.ui.admin.riwayat

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovanni.banksampah.R
import com.giovanni.banksampah.databinding.ActivityAdminRiwayatBinding
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class AdminRiwayatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminRiwayatBinding
    private lateinit var viewModel: AdminRiwayatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = getViewModel(this,dataStore)

        viewModel.getUser().observe(this) {
            if (it.level == "admin") {
                for (item in resources.getStringArray(R.array.kategori_sampah)) {
                    viewModel.fetchData(item)
                }
            }
        }

        viewModel.daftar.observe(this) {
            setUsers(it)
        }

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.setHasFixedSize(true)
        setToolbar()
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

    private fun getViewModel (activity: AppCompatActivity, dataStore: DataStore<Preferences>): AdminRiwayatViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[AdminRiwayatViewModel::class.java]
    }
    private fun setUsers(items: List<Model>){
        val adapter = AdminRiwayatAdapter(items)
        binding.rvHistory.adapter = adapter
    }

}