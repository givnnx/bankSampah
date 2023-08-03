package com.giovanni.banksampah.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.giovanni.banksampah.databinding.ActivityLoginBinding
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.giovanni.banksampah.ui.user.main.MainActivity.Companion.EXTRA_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth()
    }

    private fun auth(){
        firebaseAuth = Firebase.auth
        database = Firebase.firestore

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val user = it.result.user?.uid.toString()

                            database.collection("users").document(user)
                                .get()
                                .addOnSuccessListener { result ->
                                    val name = result.data!!["username"].toString()
                                    val level = result.data!!["level"].toString()
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.putExtra(EXTRA_USER, name)
                                    startActivity(intent)
                                    finish()
                                    Log.d("USER LOGIN", name)
                                }
                        }
                    }
                }
            }
        }
    }
}