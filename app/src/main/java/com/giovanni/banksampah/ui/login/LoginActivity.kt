package com.giovanni.banksampah.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.R
import com.giovanni.banksampah.databinding.ActivityLoginBinding
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.admin.main.AdminMainActivity
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.giovanni.banksampah.ui.user.register.RegisterUserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getViewModel()
        auth()
        daftar()
    }

    private fun getViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactoryLogin(UserPreference.getInstance(dataStore)))[LoginViewModel::class.java]
        viewModel.getUser().observe(this) { user ->
            if (user.loginState) {
                if (user.level == "user") {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_USER, user.username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else if (user.level == "admin") {
                    val intent = Intent(this@LoginActivity, AdminMainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_USER, user.username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
    private fun auth(){
        firebaseAuth = Firebase.auth
        database = Firebase.firestore

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(email, password, this@LoginActivity)
                }
            }
        }
    }

    private fun daftar(){
        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterUserActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}