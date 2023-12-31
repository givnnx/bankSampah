package com.giovanni.banksampah.ui.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.admin.main.AdminMainActivity
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    private fun loginState(){
        viewModelScope.launch {
            pref.login()
        }
    }

    private fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
    fun login(email: String, password: String, activity: Activity){
        _isLoading.value = true
        firebaseAuth = Firebase.auth
        database = Firebase.firestore
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            _isLoading.value = false
            if (it.isSuccessful){
                loginState()
                val userUid = it.result.user?.uid.toString()
                database.collection("users").document(userUid)
                    .get()
                    .addOnSuccessListener { result ->
                        val name = result.data!!["username"].toString()
                        val level = result.data!!["level"].toString()
                        val alamat = result.data!!["alamat"].toString()
                        val saldo = result.data!!["saldo"].toString().toLong()
                        val telp = result.data!!["telp"].toString()
                        Log.d("LOGIN", alamat )
                        saveUser(UserModel(userUid, name, email, level ,alamat ,saldo , true, telp))
                        if (level == "user") {
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra(MainActivity.EXTRA_USER, name)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            activity.startActivity(intent)
                            activity.finish()
                        } else if (level == "admin") {
                            val intent = Intent(activity, AdminMainActivity::class.java)
                            intent.putExtra(AdminMainActivity.EXTRA_USER, name)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            activity.startActivity(intent)
                            activity.finish()
                        }
                        Log.d("USER LOGIN", name)
                    }
            } else {
                Toast.makeText(activity, "Email atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryLogin(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}