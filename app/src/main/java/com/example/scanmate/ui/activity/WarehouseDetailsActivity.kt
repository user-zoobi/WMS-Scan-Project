package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.databinding.ActivityWarehouseDetailsBinding
import com.example.scanmate.extensions.click
import com.example.scanmate.extensions.setTransparentStatusBarColor
import com.example.scanmate.extensions.toast

class WarehouseDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWarehouseDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarehouseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        initListener()
    }

    private fun initListener(){

        binding.saveBtn.click {
            toast("Data saved")
        }

    }
}