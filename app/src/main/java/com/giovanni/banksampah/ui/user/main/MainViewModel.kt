package com.giovanni.banksampah.ui.user.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainViewModel(private val pref:UserPreference):ViewModel() {
    private lateinit var database: FirebaseFirestore

    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    fun getBalance(uid:String){
        database = Firebase.firestore
        val ref = database.collection("users").document(uid)
        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                updateSaldo(snapshot.data?.get("saldo").toString().toLong())
                Log.d(TAG, "Current data: ${snapshot.data?.get("saldo")}")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }
    fun updateSaldo(balance: Long){
        viewModelScope.launch {
            pref.saveBalance(balance)
        }
    }
}

class ViewModelFactoryMain(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}