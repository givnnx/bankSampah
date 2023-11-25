package com.giovanni.banksampah.ui.admin.daftarpermintaan

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.Repository
import com.giovanni.banksampah.ui.user.inputdata.KategoriSampah
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DaftarPermintaanViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: Repository = Repository(application)
    private val _state = MutableLiveData<Int>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val state: LiveData<Int>
        get() = _state

    val daftar = repository.getDatabyStatus("Belum diterima")
    val daftar2 = repository.getDatabyStatus("Diproses")
    private val _daftar3 = MediatorLiveData<List<Model>>()

    init {
        _daftar3.addSource(daftar) { combineLists() }
        _daftar3.addSource(daftar2) { combineLists() }
    }

    val daftar3: LiveData<List<Model>> = _daftar3

    private fun combineLists() {
        val list1 = daftar.value ?: emptyList()
        val list2 = daftar2.value ?: emptyList()
        _daftar3.value = list1 + list2
    }

    fun updateState(newState: Int) {
        _state.value = newState
    }
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    private val _Category = MutableLiveData<List<KategoriSampah>>()
    val Category: LiveData<List<KategoriSampah>> = _Category

    fun getCategory(){
        database = Firebase.firestore
        val docRef = database.collection("Jenis Sampah")
        docRef.get()
            .addOnSuccessListener {
                val CategoryList = mutableListOf<KategoriSampah>()
                for (document in it.documents){
                    val subcollectionData = document.data
                    val harga = subcollectionData?.get("Harga")
                    val kategori = KategoriSampah(document.id, harga.toString().toLong())
                    CategoryList.add(kategori)
                    Log.d("Jenis", document.id)
                    Log.d("Harga", subcollectionData?.get("Harga").toString())
                }
                _Category.value = CategoryList
            }
    }
    fun fetchData(kategori:String){
        database = Firebase.firestore
        _isLoading.value = true
        val ref = database.collection(kategori)
        ref.get()
            .addOnSuccessListener {
                updateState(1)
                _isLoading.value = false
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
                    val telp = subcollectionData?.get("telp").toString()
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
                        idPengguna = idPengguna,
                        telp = telp
                    )
                    repository.insert(user)
                }
            }
            .addOnFailureListener { e ->
                updateState(0)
                _isLoading.value = false
                Log.d("data", "Data Failed to Fetch")
            }
    }

    fun updateDataTerima(status:String, kategori: String, uid: String, nama:String){
        database = Firebase.firestore
        _isLoading.value = true
        val ref = database.collection(kategori).document(uid)
        ref.update("status", status)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
        val userRef = database.collection(nama).document(uid)
        userRef.update("status", status)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }

    fun updateSaldo(uid: String, pemasukan: Long){
        database = Firebase.firestore
        val ref = database.collection("users").document(uid)
        _isLoading.value = true
        ref.get()
            .addOnSuccessListener {
                val saldo = it.data?.get("saldo").toString().toLong()
                val saldoBaru = saldo + pemasukan
                Log.d("Saldo", saldo.toString())
                Log.d("pemasukan", pemasukan.toString())
                Log.d("Saldo Baru", saldoBaru.toString())
                ref.update("saldo", saldoBaru)
                    .addOnFailureListener {
                        _isLoading.value = false
                    }
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }
}