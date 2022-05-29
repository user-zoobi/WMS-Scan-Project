package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivitySplashBinding
import com.example.scanmate.extensions.gotoActivity
import com.example.scanmate.extensions.setTransparentStatusBarColor
import com.example.scanmate.util.LocalPreferences
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.isLogin

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        handler()
    }

    private fun handler(){

        Handler().postDelayed({
            if (LocalPreferences.getBoolean(this, isLogin)){
                gotoActivity(MenuActivity::class.java)
                finish()
            }else{
                gotoActivity(ScannerActivity::class.java)
                finish()
            }

        }, 3000)

    }
}