package com.id.etourism.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class SettingsViewModel @Inject constructor(
//    private val pref: SettingsPreferences
//) : ViewModel() {
//
//    fun getThemeSettings(): LiveData<Boolean> {
//        return pref.getThemeSetting().asLiveData()
//    }
//
//    fun saveThemeSetting(isDarkModeActive: Boolean) {
//        viewModelScope.launch {
//            pref.saveThemeSetting(isDarkModeActive)
//        }
//    }
//}