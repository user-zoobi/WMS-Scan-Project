package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.databinding.ActivityLoginBinding

class CreateCartonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}