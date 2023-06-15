package com.id.etourism.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id.etourism.data.network.model.UserData
import com.id.etourism.data.network.repository.auth.AuthRepositoryImpl
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
    ) : ViewModel() {
    private val _data = MutableLiveData<ExceptionState<UserData>>()
    val data: LiveData<ExceptionState<UserData>>
        get() = _data

    fun retrieveUserData() {
        // Get the current user's ID or any unique identifier
        val userId = authRepository.getCurrentUser()?.uid

        if (userId != null) {
            authRepository.getUserData(userId) { result ->
                _data.value = result
            }
        } else {
            // Handle the case when the user is not logged in or the ID is unavailable
        }
    }
}
