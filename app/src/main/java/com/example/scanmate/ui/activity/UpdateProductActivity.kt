package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanmate.R
import com.example.scanmate.extensions.setTransparentStatusBarColor

class UpdateProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
    }
}