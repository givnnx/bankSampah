package com.giovanni.banksampah.ui.user.gantinomor

import android.app.Activity
import android.content.ContentValues.TAG
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
import com.giovanni.banksampah.ui.user.userprofile.UserProfileActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class GantiNomorUserViewModel(private val pref: UserPreference):ViewModel() {
    private lateinit var database: FirebaseFirestore

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    fun changeNumber(password:String, telp:String, id:String, email:String, activity: Activity){
        _isLoading.value = true
        val user = FirebaseAuth.getInstance().currentUser
        database = Firebase.firestore
        val ref = database.collection("users").document(id)

        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(email, password)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            pref.editTelp(telp)
                        }
                        ref.update("telp", telp)
                            .addOnSuccessListener {
                                _isLoading.value = false
                                Toast.makeText(activity, "Nomor telah diperbarui", Toast.LENGTH_SHORT).show()
                                val intent = Intent(activity, UserProfileActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                activity.startActivity(intent)
                                activity.finish()
                            }
                    } else {
                        Log.d(TAG, "Autentikasi gagal")
                        _isLoading.value = false
                    }
                }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryGantiNomorUser(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GantiNomorUserViewModel::class.java) -> {
                GantiNomorUserViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}