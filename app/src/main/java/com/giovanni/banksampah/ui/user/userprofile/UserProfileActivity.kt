package com.giovanni.banksampah.ui.user.userprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.databinding.ActivityUserProfileBinding
import com.giovanni.banksampah.helper.Helper.rupiahFormat
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var viewModel: UserProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        getViewModel()
        setAction()
    }

    private fun getViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryUserProfile(UserPreference.getInstance(dataStore)))[UserProfileViewModel::class.java]
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

    private fun setAction() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            finishAffinity()
            val logout = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(logout)
            finish()
        }
        viewModel.getUser().observe(this) {
            Log.d("Saldo", it.saldo.toString())
            binding.balanceInfo.text = rupiahFormat(it.saldo.toInt())
        }
    }
}