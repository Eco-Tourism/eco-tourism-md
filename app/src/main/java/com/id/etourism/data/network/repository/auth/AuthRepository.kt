package com.id.etourism.data.network.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.google.gson.JsonObject
import com.id.etourism.data.network.model.UserData
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.utils.ExceptionState

interface AuthRepository {
    suspend fun register(result: (ExceptionState<String>) -> Unit, requestBody: JsonObject )

    suspend fun login(result: (ExceptionState<String>) -> Unit, requestBody: JsonObject)

    fun getUserData(userId: String, result: (ExceptionState<UserData>) -> Unit)

    fun getCurrentUser(): FirebaseUser?
}