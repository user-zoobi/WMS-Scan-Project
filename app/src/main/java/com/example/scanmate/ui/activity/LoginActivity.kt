package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityLoginBinding
import com.example.scanmate.databinding.ActivitySplashBinding
import com.example.scanmate.extensions.click
import com.example.scanmate.extensions.gotoActivity
import com.example.scanmate.extensions.setTransparentStatusBarColor
import com.example.scanmate.util.BiometricPromptUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
    }

    private fun initListeners(){

        binding.loginBtn.click {
            gotoActivity(MenuActivity::class.java)
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

}