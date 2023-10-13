package com.giovanni.banksampah.ui.admin.adminprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import kotlinx.coroutines.launch

class AdminProfileViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }
    fun logout(){
        viewModelScope.launch {
            pref.logout()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryAdminProfile(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AdminProfileViewModel::class.java) -> {
                AdminProfileViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}