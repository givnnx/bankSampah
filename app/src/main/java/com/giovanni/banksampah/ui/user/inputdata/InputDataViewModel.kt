package com.giovanni.banksampah.ui.user.inputdata

import android.app.Activity
import android.app.Application
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.Repository
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class InputDataViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: Repository = Repository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    fun addOrder(nama: String, kategori: String, berat: Int, harga: Int, tanggal: String, alamat:String, catatan: String, activity: Activity, status: String, idPengguna: String, telp: String){
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            database = Firebase.firestore
            val uid = UUID.randomUUID().toString()
            val user = Model(
                uid = uid,
                namaPengguna = nama,
                jenisSampah = kategori,
                berat = berat,
                harga = harga,
                tanggal = tanggal,
                alamat = alamat,
                catatan = catatan,
                status = status,
                idPengguna = idPengguna,
                telp = telp
            )
            val docRef = database.collection(nama).document(uid)
            docRef.set(user)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                    user.uid = uid
                    repository.insert(user)
                    _isLoading.value = false
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)
                    activity.finish()
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
            val ref = database.collection(kategori).document(uid)
            ref.set(user).addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                user.uid = uid
                repository.insert(user)
                _isLoading.value = false
                val intent = Intent(activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
                activity.finish()
            }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryInputData(private val mApplication: Application,private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(InputDataViewModel::class.java) -> {
                InputDataViewModel(mApplication, pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}