package com.example.scanmate.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityNoNetworkBinding
import com.example.scanmate.databinding.ActivityWarehouseDetailsBinding
import com.example.scanmate.extensions.click
import com.example.scanmate.extensions.gotoActivity
import com.example.scanmate.extensions.setTransparentStatusBarColor

class NoNetworkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()

    }

    private fun setupUi(){
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initListener()
    }

    private fun initListener(){
        binding.retryButton.click {
            if (isNetworkConnected(this)){
                gotoActivity(LoginActivity::class.java)
            }
        }
    }

    @SuppressLint("ServiceCast")
    private fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnectedOrConnecting
    }
}