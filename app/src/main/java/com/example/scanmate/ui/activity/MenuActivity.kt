package com.example.scanmate.ui.activity

import android.content.Intent
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
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userDesignation
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userName
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userNo
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc1
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc10
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc2
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc4
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc5
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc6
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc7
import com.example.scanmate.util.LocalPreferences.SpinnerKeys.businessLoc8
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
        initListeners()

        LocalPreferences.getString(this, userName)?.let { Log.i("value", it) }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        dialog.dismiss()
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
            }
        })

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
        binding.busSpinnerCont.click {

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
                    Log.i("LocBus","${adapter?.getItemAtPosition(position)}")

                    when(businessLocSpinner.selectedItem.toString()){
                        "BOSCH PLANT I" ->{
                            var boschLoc1 = 1
                            LocalPreferences.put(this@MenuActivity, businessLoc1, boschLoc1)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity,businessLoc1).toString())
                            )
                        }
                        "BOSCH PLANT II" ->{
                            var boschLoc2 = 2
                            LocalPreferences.put(this@MenuActivity, businessLoc2, boschLoc2)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity,
                                    businessLoc2).toString())
                            )
                        }
                        "LINZ PLANT" ->{
                            var boschLoc4 = 4
                            LocalPreferences.put(this@MenuActivity, businessLoc4, boschLoc4)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity,businessLoc4).toString())
                            )
                        }
                        "LINZ HEAD OFFICE" ->{
                            var boschLoc5 = 5
                            LocalPreferences.put(this@MenuActivity, businessLoc5, boschLoc5)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, businessLoc5).toString())
                            )
                        }
                        "BOSCH 219" ->{
                            var boschLoc6 = 6
                            LocalPreferences.put(this@MenuActivity, businessLoc6, boschLoc6)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, businessLoc6).toString())
                            )
                        }
                        "BOSCH 15" ->{
                            var boschLoc7 = 7
                            LocalPreferences.put(this@MenuActivity, businessLoc7, boschLoc7)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity,businessLoc7).toString())
                            )
                        }
                        "PIP (PVT.) LTD." ->{
                            var boschLoc8 = 8
                            LocalPreferences.put(this@MenuActivity, businessLoc8, boschLoc8)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity,businessLoc8).toString())
                            )
                        }
                        "Deutsche Bio-Sys" ->{
                            var boschLoc10 = 10
                            LocalPreferences.put(this@MenuActivity, businessLoc10, boschLoc10)
                            viewModel.userMenu(
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity, userNo).toString()),
                                Utils.getSimpleTextBody(LocalPreferences.getInt(this@MenuActivity,businessLoc10).toString())
                            )
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        binding.businessSpinner.adapter = adapter
    }

}