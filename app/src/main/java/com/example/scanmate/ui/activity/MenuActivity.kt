package com.example.scanmate.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.scanmate.R
import com.example.scanmate.data.callback.Status
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.databinding.ActivityMenuBinding
import com.example.scanmate.extensions.*
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LocalPreferences
import com.example.scanmate.util.LocalPreferences.AppConstants.orgBusLocNo
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userNo
import com.example.scanmate.util.Utils
import com.example.scanmate.viewModel.MainViewModel
import com.google.android.gms.common.util.NumberUtils

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var dialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(MainViewModel::class.java)
        initObserver()
        setupUi()

    }

    private fun setupUi() {
        supportActionBar?.hide()
        dialog = CustomProgressDialog(this)
        setTransparentStatusBarColor(R.color.transparent)
        initListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dialog.dismiss()
        finish()
    }

    private fun initObserver(){

        viewModel.userLocation(
            Utils.getSimpleTextBody(LocalPreferences.getInt(this, userNo).toString())
        )
        viewModel.userLoc.observe(this, Observer {

            when(it.status){

                Status.LOADING ->{
                    dialog.show()
                }

                Status.SUCCESS ->{

                    dialog.dismiss()
                    it.data?.get(0)?.busLocationName?.let { it1 -> Log.i("Response", it1) }
                    it.data?.get(0)?.busLocationName?.let { it1 ->
                        LocalPreferences.put(this,orgBusLocNo, it1)
                    }

                }

                Status.ERROR ->{
                    dialog.dismiss()
                }

            }
        })




        viewModel.userMenu.observe(this, Observer {

        })
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
        binding.placeCartonIV.setOnClickListener {
            gotoActivity(CreateCartonActivity::class.java)
        }

    }


    private fun openActivity(action: String) {

        val intent = Intent(this, BusinessLocationActivity::class.java)
        intent.putExtra(action, true)
        startActivity(intent)
    }

}