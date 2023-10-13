package com.giovanni.banksampah.ui.user.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.giovanni.banksampah.databinding.ActivityRegisterUserBinding
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth()
    }

    private fun auth(){
        firebaseAuth = Firebase.auth
        database = Firebase.firestore

        binding.apply {
            btnRegister.setOnClickListener{
                val username = edUsername.text.toString()
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()
                val alamat = edAddress.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password). addOnCompleteListener{
                        if(it.isSuccessful){
                            val user = it.result.user
                            if (user != null) {
                                val userData = UserModel(
                                    uid = user.uid,
                                    username = username,
                                    email = email,
                                    level = "user",
                                    alamat = alamat,
                                    saldo = 0,
                                    loginState = false
                                )

                                database.collection("users").document(user.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "DocumentSnapshot successfully written!")
                                        val intent = Intent(this@RegisterUserActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                            }
                            Toast.makeText(this@RegisterUserActivity, "Account Made", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this@RegisterUserActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@RegisterUserActivity, "Fill All First", Toast.LENGTH_SHORT).show()
                }
            }

            tvMasuk.setOnClickListener{
                val intent = Intent(this@RegisterUserActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}