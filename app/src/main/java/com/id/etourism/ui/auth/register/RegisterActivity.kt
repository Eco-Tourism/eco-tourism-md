package com.id.etourism.ui.auth.register

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.id.etourism.R
import com.id.etourism.data.local.SessionManager
import com.id.etourism.data.local.model.FormatDataLogin
import com.id.etourism.data.local.model.FormatDataRegister
import com.id.etourism.databinding.ActivityLoginBinding
import com.id.etourism.databinding.ActivityRegisterBinding
import com.id.etourism.ui.auth.login.LoginActivity
import com.id.etourism.ui.auth.login.LoginViewModel
import com.id.etourism.ui.main.MainActivity
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    @Inject lateinit var sessionManager: SessionManager
    private lateinit var dialog: Dialog
    private var handler = Handler(Looper.getMainLooper())
    private val registerViewModel : RegisterViewModel by viewModels()
    private var selectedGender: String = ""
    private var selectedKsuasana: Int = 0
    private var selectedKdaerah: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun initUi() {
        supportActionBar?.hide()
        setupGenderSpinner()
        setupDaerahSpinner()
        setupSuasanaSpinner()
        initDialog()
        initAction()
        initObserve()
    }

    private fun initObserve() {
        registerViewModel.data.observe(this) { state ->
            when (state) {
                is ExceptionState.Loading -> {
                    dialog.show()
                }

                is ExceptionState.Failure -> {
                    handler.postDelayed({
                        dialog.dismiss()
                    },2000)
                    Toast.makeText(this,state.error,Toast.LENGTH_LONG).show()
                    Timber.tag("gagal").e(state.error)
                }

                is ExceptionState.Success -> {
                    handler.postDelayed({
                        dialog.dismiss()
                        start<LoginActivity>()
                        finishAffinity()
                    },2000)
                    Toast.makeText(this,"Successful Register",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun initDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    private fun initAction() {
        binding.btnMasuk.setOnClickListener {
            val name = binding?.etNama?.text.toString().trim()
            val email = binding?.etEmail?.text.toString().trim()
            val password = binding?.etPassword?.text.toString().trim()
            val alamat = binding?.etAlamat?.text.toString().trim()
            val kontak = binding?.etNoTelp?.text.toString().trim()
            val ussername = binding?.etUsername?.text.toString().trim()
            val hobi = binding?.etHobi?.text.toString().trim()


            if (!isFormValid(name, email, password)) {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sessionManager.formatDataRegister = FormatDataRegister(email = email, hobi = hobi,
                password = password, alamat = alamat, namalengkap = name, kontak = kontak, username=ussername, jeniskelamin = selectedGender,
                kota = selectedKdaerah, suasana = selectedKsuasana
                )
            registerViewModel.register(
                Gson().fromJson(Gson().toJson(sessionManager.formatDataRegister), JsonObject::class.java)
            )
        }
        binding.imgBackRegist.setOnClickListener {
            onBackPressed()
        }
    }


    private fun setupSuasanaSpinner() {
        val suasanaSpinnerAdapter = ArrayAdapter.createFromResource(
            this, R.array.list_kuesioenr_budaya,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinKSuasana.adapter = suasanaSpinnerAdapter
        binding.spinKSuasana.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedKsuasana = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupDaerahSpinner() {
        val daerahSpinnerAdapter = ArrayAdapter.createFromResource(
            this, R.array.list_kuesioenr_place,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinKdaerah.adapter = daerahSpinnerAdapter
        binding.spinKdaerah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedKdaerah = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun setupGenderSpinner() {
        val genderSpinnerAdapter = ArrayAdapter.createFromResource(
            this, R.array.list_gender,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinGender.adapter = genderSpinnerAdapter
        binding.spinGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGender = parent?.getItemAtPosition(position).toString()
                Log.e("gender",selectedGender)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun isFormValid(name: String, email: String, password: String): Boolean {
        val isNameValid = name.isNotEmpty()
        val isEmailValid = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 8

        return isNameValid && isEmailValid && isPasswordValid
    }
}