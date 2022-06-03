package com.example.scanmate.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.scanmate.R
import com.example.scanmate.data.callback.Status
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.databinding.ActivityMenuBinding
import com.example.scanmate.extensions.click
import com.example.scanmate.extensions.gotoActivity
import com.example.scanmate.extensions.obtainViewModel
import com.example.scanmate.extensions.setTransparentStatusBarColor
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LocalPreferences
import com.example.scanmate.util.LocalPreferences.AppConstants.orgBusLocNo
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.PREF
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.busLocNo
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userNo
import com.example.scanmate.util.Utils
import com.example.scanmate.viewModel.MainViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var dialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(MainViewModel::class.java)
        setupUi()
        initObserver()
    }

    private fun setupUi() {
        supportActionBar?.hide()
        dialog = CustomProgressDialog(this)
        setTransparentStatusBarColor(R.color.transparent)

        binding.toolbar.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            clearPreferences(this)
            true
        }
        binding.userNameTV.text = LocalPreferences.getString(this,
            LocalPreferences.AppLoginPreferences.userName
        )
        binding.userDesignTV.text = LocalPreferences.getString(this,
            LocalPreferences.AppLoginPreferences.userDesignation
        )
        binding.loginTimeTV.text = LocalPreferences.getString(this,
            LocalPreferences.AppLoginPreferences.loginTime
        )

        initListeners()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun initObserver(){

        /**
         *  User location api observer
         */
        viewModel.userLocation(
            Utils.getSimpleTextBody(LocalPreferences.getInt(this, userNo).toString())
        )
        viewModel.userLoc.observe(this, Observer {

            when(it.status){
                Status.LOADING -> {
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    it.data?.get(0)?.busLocationName?.let { it1 -> Log.i("Response", it1) }
                    it.data?.get(0)?.busLocationName?.let { it1 ->
                        LocalPreferences.put(this,orgBusLocNo, it1)
                    }
                    it.data?.let { it1 -> showListInSpinner(it1) }
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }})

        /**
         *  User Menu api observer
         */
        viewModel.userMenu.observe(this, Observer {
            when(it.status){
                Status.LOADING -> dialog.show()
                Status.SUCCESS ->{
                    dialog.dismiss()
                    Log.i("businessLoc1",it.data?.get(0)?.menu!!)
                }
                Status.ERROR -> dialog.dismiss()
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
        binding.scanCartonIV.click {
            gotoActivity(ScannerActivity::class.java, "scannerKey",true)
        }
    }

    private fun openActivity(action: String) {
        val intent = Intent(this, BusinessLocationActivity::class.java)
        intent.putExtra(action, true)
        startActivity(intent)
    }

    private fun showListInSpinner(data:List<UserLocationResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val businessLocSpinner = binding.businessSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].busLocationName
            binding.businessSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                    Log.i("LocBus","${data[position].orgBusLocNo}")
                    data[position].orgBusLocNo?.let {
                        LocalPreferences.put(this@MenuActivity, busLocNo , it)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        binding.businessSpinner.adapter = adapter
    }

    private fun clearPreferences(context: Context){
        val settings: SharedPreferences =
            context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        settings.edit().clear().apply()
        finish()
    }
}