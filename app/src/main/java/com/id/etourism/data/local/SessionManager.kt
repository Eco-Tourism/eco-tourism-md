package com.id.etourism.data.local

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import com.id.etourism.data.local.model.FormatDataLogin
import com.id.etourism.data.local.model.FormatDataRegister
import com.id.etourism.data.network.response.PostLoginResponse
import com.id.etourism.data.network.response.PostRegisterResponse
import com.id.etourism.ui.auth.login.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import splitties.activities.start

class SessionManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    companion object {
        private const val PREF_NAME = "dataLoginEtourismCache"
        private const val DATA_LOGIN = "dataLoginCache"
        private const val DATA_SIGNIN = "dataSignCache"
        private const val DATA_SIGNUP = "dataSignUp"
        private const val DATA_REGISTER = "dataRegister"
        private const val DATA_USER = "dataLoginCache"
        val PREF_IS_LOGIN = "LOGIN"
        val PREF_IS_REGISTER = "REGISTER"
        val MODE = "MODE"
    }

    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var privateMode = 0

    init {
        pref = context.getSharedPreferences(PREF_NAME, privateMode)
        editor = pref.edit()
        editor.apply()
    }

    var dataLogin: PostLoginResponse?
        set(value) = pref.edit().putString(DATA_LOGIN, Gson().toJson(value)).apply()
        get() {
            val data: String? = pref.getString(DATA_LOGIN, null)
            return if (data.isNullOrBlank()) {
                null
            } else {
                Gson().fromJson(data, PostLoginResponse::class.java)
            }
        }

    var formatDataLogin: FormatDataLogin?
        set(value) = pref.edit().putString(DATA_SIGNIN, Gson().toJson(value)).apply()
        get() {
            val data: String? = pref.getString(DATA_SIGNIN, null)
            return if (data.isNullOrBlank()) {
                null
            } else {
                Gson().fromJson(data, FormatDataLogin::class.java)
            }
        }
    var dataRegister: PostRegisterResponse?
        set(value) = pref.edit().putString(DATA_REGISTER, Gson().toJson(value)).apply()
        get() {
            val data: String? = pref.getString(DATA_REGISTER, null)
            return if (data.isNullOrBlank()) {
                null
            } else {
                Gson().fromJson(data, PostRegisterResponse::class.java)
            }
        }
    var formatDataRegister: FormatDataRegister?
        set(value) = pref.edit().putString(DATA_SIGNUP, Gson().toJson(value)).apply()
        get() {
            val data: String? = pref.getString(DATA_SIGNUP, null)
            return if (data.isNullOrBlank()) {
                null
            } else {
                Gson().fromJson(data, FormatDataRegister::class.java)
            }
        }
    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String): String? {
        return pref.getString(key, "")
    }

    fun put(key: String, value: Int) {
        editor.putInt(key, value)
            .apply()
    }

    fun getInt(key: String): Int? {
        return pref.getInt(key, 0)
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun clear() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.apply()

        // Starting Login Activity
        context.start<LoginActivity>() {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}