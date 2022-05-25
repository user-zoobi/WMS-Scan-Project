package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.VideoView
import androidx.lifecycle.Observer
import com.example.scanmate.R
import com.example.scanmate.data.callback.Status
import com.example.scanmate.databinding.ActivityLoginBinding
import com.example.scanmate.databinding.ActivitySplashBinding
import com.example.scanmate.extensions.*
import com.example.scanmate.util.BiometricPromptUtils
import com.example.scanmate.util.Constants.LogMessages.success
import com.example.scanmate.util.Utils
import com.example.scanmate.viewModel.MainViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        viewModel = obtainViewModel(MainViewModel::class.java)

        viewModel.data.observe(this, Observer {
            it.let {
                when (it.status) {

                    Status.LOADING -> {
                        binding.progressDialog.visible()
                    }

                    Status.SUCCESS -> {
                        binding.progressDialog.gone()
                        it.let {
                            Log.i(success,"${it.data?.get(0)?.emailID}")
                            gotoActivity(MenuActivity::class.java)
                        }
                    }

                    Status.ERROR -> {
                        binding.progressDialog.gone()
                    }

                }
            }
        })
    }

    private fun initListeners() {

        binding.loginBtn.click {
            validations()
        }

        binding.fingerPrintIV.click {
            showBiometricPrompt()
        }

    }

    private fun showBiometricPrompt() {
        val biometricPromptUtils = BiometricPromptUtils(
            this,
            object : BiometricPromptUtils.BiometricListener {

                override fun onAuthenticationLockoutError() {

                }

                override fun onAuthenticationPermanentLockoutError() {

                }

                override fun onAuthenticationSuccess() {
                    gotoActivity(MenuActivity::class.java)
                }

                override fun onAuthenticationFailed() {

                }

                override fun onAuthenticationError() {

                }

            })
        biometricPromptUtils.showBiometricPrompt(
            resources.getString(R.string.confirmYourBiometricsKey),
            resources.getString(R.string.cancelKey),
            confirmationRequired = false
        )
    }

    private fun validations() {

        val userID = binding.userIdET.text.toString()
        val password = binding.passwordET.text.toString()

        if (userID.isNullOrEmpty() or password.isNullOrEmpty()) {
            toast("Field must be empty")
        } else {
            viewModel.loginUser(
                Utils.getSimpleTextBody(userID),
                Utils.getSimpleTextBody(password)
            )
        }
    }

}