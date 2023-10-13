package com.giovanni.banksampah.ui.admin.daftarpermintaan

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.Repository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DaftarPermintaanViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: Repository = Repository(application)
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
    fun fetchData(kategori:String){
        database = Firebase.firestore
        val ref = database.collection(kategori)
        ref.get()
            .addOnSuccessListener {
                updateState(1)
                for (document in it.documents) {
                    val subcollectionData = document.data
                    val uid = subcollectionData?.get("uid").toString()
                    val namaPengguna = subcollectionData?.get("namaPengguna").toString()
                    val kategori = subcollectionData?.get("jenisSampah").toString()
                    val berat = subcollectionData?.get("berat").toString().toInt()
                    val harga = subcollectionData?.get("harga").toString().toInt()
                    val tanggal = subcollectionData?.get("tanggal").toString()
                    val alamat = subcollectionData?.get("alamat").toString()
                    val catatan = subcollectionData?.get("catatan").toString()
                    val status = subcollectionData?.get("status").toString()
                    val idPengguna = subcollectionData?.get("idPengguna").toString()
                    Log.d("subcollection_data", subcollectionData.toString())

                    val user = Model(
                        uid = uid,
                        namaPengguna = namaPengguna,
                        jenisSampah = kategori,
                        berat = berat,
                        harga = harga,
                        tanggal = tanggal,
                        alamat = alamat,
                        catatan = catatan,
                        status = status,
                        idPengguna = idPengguna
                    )
                    repository.insert(user)
                }
            }
            .addOnFailureListener { e ->
                updateState(0)
                Log.d("data", "Data Failed to Fetch")
            }
    }

    fun updateDataTerima(status:String, kategori: String, uid: String, nama:String){
        database = Firebase.firestore

        val ref = database.collection(kategori).document(uid)
        ref.update("status", status)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        val userRef = database.collection(nama).document(uid)
        userRef.update("status", status)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
    }

    fun updateSaldo(uid: String, pemasukan: Long){
        database = Firebase.firestore
        var saldo:Long = 0
        val ref = database.collection("users").document(uid)
        ref.get()
            .addOnSuccessListener {
                saldo = it.data?.get("saldo").toString().toLong()
            }
        val saldoBaru = saldo + pemasukan
        ref.update("saldo", saldoBaru)
    }
}