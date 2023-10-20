package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.TarikSaldoModel
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.TarikSaldoRepository
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DaftarPermintaanTarikSaldoViewModel(application: Application, private val pref:UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: TarikSaldoRepository = TarikSaldoRepository(application)
    private val _state = MutableLiveData<Int>()

    val state: LiveData<Int>
        get() = _state

    val daftar = repository.getDatabyStatus("Belum diterima")

    fun updateState(newState: Int) {
        _state.value = newState
    }

    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    fun fetchData(){
        database = Firebase.firestore
        val ref = database.collection("Penarikan Saldo")
        ref.get()
            .addOnSuccessListener {
                updateState(1)
                for (document in it.documents) {
                    val subcollectionData = document.data
                    val uid = subcollectionData?.get("uid").toString()
                    val namaPengguna = subcollectionData?.get("username").toString()
                    val tanggal = subcollectionData?.get("tanggal").toString()
                    val status = subcollectionData?.get("status").toString()
                    val idPengguna = subcollectionData?.get("idPengguna").toString()
                    val jumlah = subcollectionData?.get("jumlah").toString().toLong()
                    val saldo = subcollectionData?.get("saldo").toString().toLong()
                    Log.d("subcollection_data", subcollectionData.toString())

                    val user = TarikSaldoModel(
                        uid = uid,
                        username = namaPengguna,
                        tanggal = tanggal,
                        status = status,
                        idPengguna = idPengguna,
                        jumlah = jumlah,
                        saldo = saldo
                    )
                    repository.insert(user)
                }
            }
            .addOnFailureListener { e ->
                updateState(0)
                Log.d("data", "Data Failed to Fetch")
            }
    }

    fun updateSaldo(uid: String, jumlah: Long, id: String){
        database = Firebase.firestore
        val ref = database.collection("users").document(uid)
        val penarikanRef = database.collection("Penarikan Saldo").document(id)
        ref.get()
            .addOnSuccessListener {
                val saldo = it.data?.get("saldo").toString().toLong()
                val saldoBaru = saldo - jumlah
                Log.d("Saldo", saldo.toString())
                Log.d("pemasukan", jumlah.toString())
                Log.d("Saldo Baru", saldoBaru.toString())
                ref.update("saldo", saldoBaru)
                penarikanRef.update("status", "Diterima")
            }
    }
}