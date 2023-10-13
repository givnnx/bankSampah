package com.giovanni.banksampah.ui.user.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.giovanni.banksampah.model.UserModel
import com.giovanni.banksampah.model.UserPreference

class MainViewModel(private val pref:UserPreference):ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.gettingUser().asLiveData()
    }
}

class ViewModelFactoryMain(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }
}