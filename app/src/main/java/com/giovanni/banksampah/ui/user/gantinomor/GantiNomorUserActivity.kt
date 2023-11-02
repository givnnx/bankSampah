package com.giovanni.banksampah.ui.user.gantinomor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.databinding.ActivityGantiNomorUserBinding
import com.giovanni.banksampah.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class GantiNomorUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGantiNomorUserBinding
    private lateinit var viewModel: GantiNomorUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGantiNomorUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        getViewModel()
        setAction()
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setAction(){
        binding.apply {
            btnTarik.setOnClickListener {
                val password = passVerif.text.toString()
                val newTelp = inputTelp.text.toString()
                viewModel.getUser().observe(this@GantiNomorUserActivity){
                    Log.d("Email", it.email)
                    Log.d("Password",password)
                    Log.d("telp", newTelp)
                    Log.d("UID", it.uid)
                    viewModel.changeNumber(password, newTelp, it.uid, it.email, this@GantiNomorUserActivity)
                }
            }
        }
    }

    private fun getViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryGantiNomorUser(UserPreference.getInstance(dataStore)))[GantiNomorUserViewModel::class.java]
    }


    private fun setToolbar() {
        setSupportActionBar(binding.tbGantiNmr)
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
                pbTarikSld.visibility = View.VISIBLE
                overlayView.visibility = View.VISIBLE
                btnTarik.isEnabled = false
            } else {
                pbTarikSld.visibility = View.GONE
                overlayView.visibility = View.GONE
                btnTarik.isEnabled = true
            }
        }
    }
}