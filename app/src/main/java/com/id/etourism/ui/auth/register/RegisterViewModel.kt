package com.id.etourism.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.id.etourism.data.network.repository.auth.AuthRepository
import com.id.etourism.data.network.repository.auth.AuthRepositoryImpl
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val authRepository: AuthRepository
    ) : ViewModel() {
    private val _data = MutableLiveData<ExceptionState<String>>()
    val data: LiveData<ExceptionState<String>>
        get() = _data

    fun register(requestBody : JsonObject) {
        _data.value = ExceptionState.Loading
        viewModelScope.launch {
            authRepository.register({_data.value=it},requestBody )
        }

    }
}