package com.id.etourism.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.id.etourism.data.local.SessionManager
import com.id.etourism.databinding.ActivityProfileBinding
import com.id.etourism.ui.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        try {
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }



    }

    private fun initUi() {
        supportActionBar?.hide()
        binding.switchmode.isChecked = sessionManager.getBoolean(SessionManager.MODE)
//        viewModel.retrieveUserData()
        initAction()
        initObserve()
        binding.tvNamaLengkap.text = sessionManager.dataLogin?.namalengkap
        binding.tvAlamat.text = sessionManager.dataLogin?.alamat
        binding.tvEmail.text = sessionManager.dataLogin?.email
        binding.tvnoHp.text = sessionManager.dataLogin?.kontak
        binding.tvJenisKelamin.text = sessionManager.dataLogin?.jeniskelamin
    }

    private fun initObserve() {

//        viewModel.data.observe(this) { state ->
//            when (state) {
//                is ExceptionState.Success -> {
//                    binding.tvName.text = state.data.name
//                    binding.tvEmail.text = state.data.email
//                }
//
//                is ExceptionState.Failure -> {
//                    // Handle the failure state
//                    Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
//                }
//
//                is ExceptionState.Loading -> {
//                    // Show loading progress, such as displaying a progress bar
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//            }
//        }
    }

    private fun initAction() {

        /** Action Setting */
        binding.switchmode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            sessionManager.put(SessionManager.MODE,isChecked)
        }

        binding.imgBackProfile.setOnClickListener {
            onBackPressed()
        }
        binding.btnLogout.setOnClickListener {
            sessionManager.put(SessionManager.PREF_IS_LOGIN,false)
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
        }

    }
}
