package com.giovanni.banksampah.ui.user.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference
import kotlinx.coroutines.launch

class UserProfileViewModel(private val pref: UserPreference): ViewModel() {
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
class ViewModelFactoryUserProfile(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserProfileViewModel::class.java) -> {
                UserProfileViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}