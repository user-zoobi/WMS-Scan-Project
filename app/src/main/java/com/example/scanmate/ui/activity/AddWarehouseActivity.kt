package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityAddWarehouseBinding
import com.example.scanmate.databinding.ActivityLoginBinding

class AddWarehouseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWarehouseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWarehouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}