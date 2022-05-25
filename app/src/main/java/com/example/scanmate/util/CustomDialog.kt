package com.example.scanmate.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.scanmate.R

class CustomDialog (context: Context) : Dialog(context, R.style.customDialog) {

    init {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        LayoutInflater.from(context).inflate(R.layout.custom_dialog, viewGroup, false)
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        //window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.custom_dialog)
    }
}