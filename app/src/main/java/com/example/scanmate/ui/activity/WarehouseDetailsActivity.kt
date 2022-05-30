package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.scanmate.R
import com.example.scanmate.data.callback.Status
import com.example.scanmate.data.routes.Routes.EndPoint.userLoc
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.databinding.ActivityWarehouseDetailsBinding
import com.example.scanmate.extensions.click
import com.example.scanmate.extensions.obtainViewModel
import com.example.scanmate.extensions.setTransparentStatusBarColor
import com.example.scanmate.extensions.toast
import com.example.scanmate.util.Constants.WMSStructure.pallets
import com.example.scanmate.util.Constants.WMSStructure.racks
import com.example.scanmate.util.Constants.WMSStructure.shelf
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LocalPreferences
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userNo
import com.example.scanmate.viewModel.MainViewModel

class WarehouseDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWarehouseDetailsBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var dialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarehouseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(MainViewModel::class.java)
        setupUi()
        initListener()
        initObserver()

    }

    private fun setupUi(){
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        dialog = CustomProgressDialog(this)

        when {
            intent.extras?.getBoolean(racks) == true ->
            {
                binding.editDetailTV.text = "Edit racks details"
            }
            intent.extras?.getBoolean(shelf) == true ->
            {
                binding.editDetailTV.text = "Edit shelf details"
            }
            intent.extras?.getBoolean(pallets) == true ->
            {
                binding.editDetailTV.text = "Edit pallets details"
            }
        } }

    private fun initListener(){

        val warehouseName = binding.warehouseName.text.toString()
        binding.saveBtn.click {
            viewModel.addUpdateWarehouse(
                "3", "wms101", "WH-1",
                "1",
                "2",
                "Test-PC"
            )
        }

    }

    private fun initObserver(){

        viewModel.addUpdateWarehouse.observe(this, Observer {

            when(it.status){

                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    Log.i("addWarehouse","${it.data?.error}")
                    toast(it.data?.error.toString())
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }

            }
        })
    }

}