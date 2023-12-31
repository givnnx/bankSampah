package com.giovanni.banksampah.ui.user.riwayat

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.Repository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RiwayatViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: Repository = Repository(application)

    val riwayat = repository.getData()

    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }
    fun fetchData(nama: String){
        database = Firebase.firestore
        Log.d("NAMA", nama)
        val ref = database.collection(nama)
        ref.get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    val subcollectionData = document.data
                    Log.d(TAG, subcollectionData.toString())
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
                    val telp = subcollectionData?.get("telp").toString()

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
                        idPengguna = idPengguna,
                        telp = telp
                    )
                    repository.insert(user)
                }
            }
            .addOnFailureListener { e ->
                Log.d("data", "Data Failed to Fetch")
            }
    }
    fun deleteData(uid: String){
        repository.deleteData(uid)
    }
}