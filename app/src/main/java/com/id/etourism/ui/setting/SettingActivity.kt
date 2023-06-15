package com.id.etourism.ui.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.id.etourism.R
import com.id.etourism.ui.auth.login.LoginActivity
//
//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//
//class SettingActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_setting)
//        supportActionBar?.title = "Settings"
//        auth = Firebase.auth
//
//        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
//
//        val settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingsViewModel::class.java]
//
//        settingsViewModel.getThemeSettings().observe(this
//        ) { isDarkModeActive: Boolean ->
//            if (isDarkModeActive) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchTheme.isChecked = true
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchTheme.isChecked = false
//            }
//        }
//
//        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            settingsViewModel.saveThemeSetting(isChecked)
//        }
//        findViewById<Button>(R.id.btn_logout).setOnClickListener{
//            signOut()
//        }
//    }
//    private fun signOut() {
//        auth.signOut()
//        startActivity(Intent(this, LoginActivity::class.java))
//        finish()
//    }
//}