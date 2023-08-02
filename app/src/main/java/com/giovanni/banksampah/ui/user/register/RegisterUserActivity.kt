package com.giovanni.banksampah.ui.user.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.giovanni.banksampah.databinding.ActivityRegisterUserBinding
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth()
    }

    private fun auth(){
        firebaseAuth = Firebase.auth
        firebaseDatabase  = Firebase.database("https://banksampah-api-default-rtdb.asia-southeast1.firebasedatabase.app/")

        binding.apply {
            btnRegister.setOnClickListener{
                val username = edUsername.text.toString()
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password). addOnCompleteListener{
                        if(it.isSuccessful){
                            val user = it.result.user
                            if (user != null) {
                                val userData = UserModel(
                                    uid = user.uid,
                                    username = username,
                                    email = email,
                                    password = password,
                                    level = "user"
                                )
                                val reference = firebaseDatabase.getReference("users").child(user.uid)

                                reference.setValue(userData).addOnCompleteListener{dataTask ->
                                    if(dataTask.isSuccessful){
                                        Toast.makeText(this@RegisterUserActivity, "dataStored", Toast.LENGTH_LONG).show()
                                        val intent = Intent(this@RegisterUserActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this@RegisterUserActivity, "Operation Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
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