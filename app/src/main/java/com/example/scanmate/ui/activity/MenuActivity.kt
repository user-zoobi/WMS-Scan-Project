package com.example.scanmate.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityAddWarehouseBinding
import com.example.scanmate.databinding.ActivityLoginBinding
import com.example.scanmate.databinding.ActivityMenuBinding
import com.example.scanmate.extensions.gotoActivity
import com.example.scanmate.extensions.setTransparentStatusBarColor

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
    }

    private fun setupUi() {

        initListeners()
    }

    private fun initListeners() {
        binding.warehouseIV.setOnClickListener {
            openActivity("warehouseKey")
        }
        binding.racksIV.setOnClickListener {
            openActivity("rackKey")
        }
        binding.shelfIV.setOnClickListener {
            openActivity("shelfKey")
        }
        binding.palletsIV.setOnClickListener {
            openActivity("palletKey")
        }
        binding.createCartonBtn.setOnClickListener {
            gotoActivity(CreateCartonActivity::class.java)
        }

    }

    private fun openActivity(action: String) {

        val intent = Intent(this, BusinessLocationActivity::class.java)
        intent.putExtra(action, true)
        startActivity(intent)
    }
}