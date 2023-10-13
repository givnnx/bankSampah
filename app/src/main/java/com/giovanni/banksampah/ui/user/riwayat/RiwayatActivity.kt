package com.giovanni.banksampah.ui.user.riwayat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovanni.banksampah.databinding.ActivityRiwayatBinding
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class RiwayatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var viewModel: RiwayatViewModel
    var nama = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        viewModel = getViewModel(this, dataStore)

        viewModel.getUser().observe(this, {
            nama = it.username
            viewModel.fetchData(nama)
        })

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.setHasFixedSize(true)

        viewModel.riwayat.observe(this) {
            setUsers(it)
        }
    }

    private fun getViewModel (activity: AppCompatActivity, dataStore: DataStore<Preferences>): RiwayatViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, UserPreference.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[RiwayatViewModel::class.java]
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

    private fun setUsers(items: List<Model>){
        val adapter = RiwayatAdapter(items, viewModel)
        binding.rvHistory.adapter = adapter
    }
}