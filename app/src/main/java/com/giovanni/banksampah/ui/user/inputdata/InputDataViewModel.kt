package com.giovanni.banksampah.ui.user.inputdata

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.repository.Repository
import com.giovanni.banksampah.ui.login.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InputDataViewModel(application: Application, private val pref: UserPreference): ViewModel() {
    private val repository: Repository = Repository(application)

    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }

    fun addOrder(nama: String, kategori: String, berat: Int, harga: Int, tanggal: String, alamat:String, catatan: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Model(
                namaPengguna = nama,
                jenisSampah = kategori,
                berat = berat,
                harga = harga,
                tanggal = tanggal,
                alamat = alamat,
                catatan = catatan
            )
            repository.insert(user)
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