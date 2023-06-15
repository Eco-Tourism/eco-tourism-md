package com.id.etourism.ui.auth.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.id.etourism.R
import com.id.etourism.data.local.SessionManager
import com.id.etourism.data.local.model.FormatDataLogin
import com.id.etourism.databinding.ActivityLoginBinding
import com.id.etourism.ui.auth.register.RegisterActivity
import com.id.etourism.ui.main.MainActivity
import com.id.etourism.ui.main.MainViewModel
import com.id.etourism.utils.ExceptionState
import com.id.etourism.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.activities.start
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject lateinit var sessionManager:SessionManager
    private lateinit var dialog: Dialog
    private lateinit var binding : ActivityLoginBinding
    private var isLoading = false
    private var handler = Handler(Looper.getMainLooper())
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        try {
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initUi() {
        initDialog()
        initAction()
        initObserve()
    }

    private fun initDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    private fun initObserve() {
        loginViewModel.data.observe(this) { state ->
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
                        Timber.tag("success").e("${state.data}")
                        start<MainActivity>()
                        finishAffinity()
                    },3000)
                    sessionManager.put(SessionManager.PREF_IS_LOGIN,true)
                    isLoading = false
                    Toast.makeText(this,"Successful Login",Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun initAction() {
        binding.btnMasuk.setOnClickListener {
            val email = binding?.etUsername?.text.toString().trim()
            val password = binding?.etPassword?.text.toString().trim()

            if (!isFormValid(email, password)) {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            isLoading = true

            sessionManager.formatDataLogin = FormatDataLogin(username = email, password = password)
            loginViewModel.login(
                Gson().fromJson(Gson().toJson(sessionManager.formatDataLogin), JsonObject::class.java)
            )
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun isFormValid(email: String, password: String): Boolean {
        val isEmailValid = email.isNotEmpty()
//                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty()
//                && password.length >= 8

        return isEmailValid && isPasswordValid
    }
}