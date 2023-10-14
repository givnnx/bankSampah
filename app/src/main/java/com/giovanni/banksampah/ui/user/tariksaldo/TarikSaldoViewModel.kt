package com.giovanni.banksampah.ui.user.tariksaldo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import com.giovanni.banksampah.ui.admin.adminprofile.AdminProfileViewModel

class TarikSaldoViewModel(private val pref:UserPreference): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
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