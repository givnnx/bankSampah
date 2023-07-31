package com.giovanni.banksampah.ui.riwayat

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovanni.banksampah.databinding.ActivityRiwayatBinding
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.ui.ViewModelFactory

class RiwayatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var viewModel: RiwayatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        viewModel = getViewModel(this)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.setHasFixedSize(true)

        viewModel.riwayat.observe(this) {
            setUsers(it)
        }
    }

    private fun getViewModel (activity: AppCompatActivity): RiwayatViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
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
        val adapter = RiwayatAdapter(items)
        binding.rvHistory.adapter = adapter
    }
}