package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityScannerBinding
import com.example.scanmate.extensions.click
import com.example.scanmate.extensions.gotoActivity
import com.example.scanmate.extensions.setTransparentStatusBarColor

class ScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
    }

    private fun initListeners(){

        binding.scanBtn.click {
            gotoActivity(ScannerCameraActivity::class.java)
        }

        binding.loginBtn.click {
            gotoActivity(LoginActivity::class.java)
        }

    }
}