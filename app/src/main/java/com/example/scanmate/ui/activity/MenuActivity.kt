package com.example.scanmate.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.scanmate.R
import com.example.scanmate.data.callback.Status
import com.example.scanmate.databinding.ActivityMenuBinding
import com.example.scanmate.extensions.*
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LoginPreferences
import com.example.scanmate.util.LoginPreferences.AppConstants.orgBusLocNo
import com.example.scanmate.util.LoginPreferences.AppLoginPreferences.userNo
import com.example.scanmate.util.Utils
import com.example.scanmate.viewModel.MainViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var dialog: CustomProgressDialog
    private val item:MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = CustomProgressDialog(this)
        viewModel = obtainViewModel(MainViewModel::class.java)
        setupUi()

    }

    private fun setupUi() {
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        initListeners()
        initObserver()
    }

    private fun initObserver(){

        viewModel.userMenu(
            Utils.getSimpleTextBody(LoginPreferences.getInt(this,userNo).toString()),
            Utils.getSimpleTextBody(LoginPreferences.getInt(this,orgBusLocNo).toString())
        )
        Log.i("userValue",LoginPreferences.getInt(this,userNo).toString())

        viewModel.userMenu.observe(this, Observer {
            when(it.status){

                Status.LOADING ->{
                    dialog.show()
                }

                Status.SUCCESS ->{
                    dialog.dismiss()

                    Log.i("userValue", it.data?.get(0)?.status.toString())

                    it.let { response->
                        response.data?.forEach { value->
                            value.menu?.let { it1 -> item.add(it1) }
                        }
                    }
                }

                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
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