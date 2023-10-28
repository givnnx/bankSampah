package com.giovanni.banksampah.ui.user.tariksaldo

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.TarikSaldoModel
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.user.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class TarikSaldoViewModel(private val pref:UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendRequest(jumlah: Long, saldo: Long, status: String, tanggal:String, username:String, activity: Activity, idPengguna: String){
        _isLoading.value = true
        database = Firebase.firestore
        val uid = UUID.randomUUID().toString()
        val user = TarikSaldoModel(
            uid, username, jumlah, status, tanggal, saldo, idPengguna
        )
        val ref = database.collection("Penarikan Saldo").document(uid)
        ref.set(user)
            .addOnSuccessListener {
                _isLoading.value = false
                Log.d("Kirim Data", "DocumentSnapshot successfully written!")
                Toast.makeText(activity, "Permintaan Berhasil Dikirim", Toast.LENGTH_SHORT).show()
                finishAffinity(activity)
                val logout = Intent(activity, MainActivity::class.java)
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(logout)
                activity.finish()
            }
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryTarikSaldo(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TarikSaldoViewModel::class.java) -> {
                TarikSaldoViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}