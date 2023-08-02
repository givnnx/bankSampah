package com.giovanni.banksampah.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.giovanni.banksampah.databinding.ActivityLoginBinding
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth()
    }

    private fun auth(){
        firebaseAuth = Firebase.auth
        database = Firebase.database("https://banksampah-api-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val user = it.result.user?.uid.toString()

                            database.child("users").child(user).addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val currentUser:UserModel? = snapshot.getValue(UserModel::class.java)

                                    if (currentUser != null) {
                                        val username = currentUser.username
                                        val level = currentUser.level
                                        val uid = currentUser.uid

                                        Log.d("Login Level", "$username, $level, $uid")

                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.putExtra(MainActivity.EXTRA_USER, username)
                                        startActivity(intent)
                                        finish()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}