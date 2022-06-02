package com.example.scanmate.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanmate.R
import com.example.scanmate.adapter.recyclerview.PalletsAdapter
import com.example.scanmate.adapter.recyclerview.RacksAdapter
import com.example.scanmate.adapter.recyclerview.ShelfAdapter
import com.example.scanmate.adapter.recyclerview.WarehouseAdapter
import com.example.scanmate.data.callback.Status
import com.example.scanmate.data.response.GetRackResponse
import com.example.scanmate.data.response.GetShelfResponse
import com.example.scanmate.data.response.GetWarehouseResponse
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.extensions.*
import com.example.scanmate.util.Constants.WMSStructure.pallets
import com.example.scanmate.util.Constants.WMSStructure.racks
import com.example.scanmate.util.Constants.WMSStructure.shelf
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LocalPreferences
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.busLocNo
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.rackNo
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userNo
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.whNo
import com.example.scanmate.util.Utils
import com.example.scanmate.viewModel.MainViewModel

class BusinessLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessLocationBinding
    private lateinit var warehouseAdapter: WarehouseAdapter
    private lateinit var racksAdapter: RacksAdapter
    private lateinit var shelfAdapter: ShelfAdapter
    private lateinit var palletAdapter: PalletsAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var dialog: CustomProgressDialog
    private lateinit var list: ArrayList<GetWarehouseResponse>
    private lateinit var rackList: ArrayList<GetRackResponse>
    private lateinit var shelfList: ArrayList<GetShelfResponse>


    private var screen = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = CustomProgressDialog(this)
        viewModel = obtainViewModel(MainViewModel::class.java)
        initObserver()
        setupUi()
        Log.i("getBusLocNo", LocalPreferences.getInt(this, busLocNo).toString())
    }

    private fun initObserver(){

        /**
         *  user location
         */
        viewModel.userLocation(
            Utils.getSimpleTextBody(
                LocalPreferences.getInt(this@BusinessLocationActivity, userNo).toString()
            ))
        viewModel.userLoc.observe(this, Observer {
            when(it.status){
                Status.LOADING->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    showBusLocSpinner(it.data!!)
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
        })

        /**
         *  get warehouse
         */
        viewModel.getWarehouse(
            "",LocalPreferences.getInt(this, busLocNo).toString()
        )
        viewModel.getWarehouse.observe(this,Observer{
            when(it.status){
                Status.LOADING->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    it.data?.get(0)?.wHName?.let { it1 -> Log.i("warehouseResponse", it1) }
                    showWarehouseSpinner(it.data!!)
                    list = it.data as ArrayList<GetWarehouseResponse>
                    warehouseAdapter.addItems(list)
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
        })

        /**
         *  get rack
         */
        viewModel.getRack(
            Utils.getSimpleTextBody(""),
            Utils.getSimpleTextBody("${LocalPreferences.getInt(this, whNo)}"),
            Utils.getSimpleTextBody("${LocalPreferences.getInt(this, busLocNo)}")
        )
        viewModel.getRack.observe(this, Observer{
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    Log.i("getRack",it.data?.get(0)?.rackNo.toString())
                    showRackSpinner(it.data!!)
                    rackList = it.data as ArrayList<GetRackResponse>
                    racksAdapter.addItems(rackList)
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
        })

        /**
         *  get shelf
         */
        viewModel.getShelf(
            Utils.getSimpleTextBody(""),
            Utils.getSimpleTextBody("${LocalPreferences.getInt(this,rackNo)}"),
            Utils.getSimpleTextBody("${LocalPreferences.getInt(this, userNo)}")
        )
        viewModel.getShelf.observe(this,Observer{
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    showShelfSpinner(it.data!!)
                    shelfList = it.data as ArrayList<GetShelfResponse>
                    shelfAdapter.addItems(shelfList)
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
        })
    }

    private fun setupUi(){
        binding.userNameTV.text = LocalPreferences.getString(this,
            LocalPreferences.AppLoginPreferences.userName
        )
        binding.userDesignTV.text = LocalPreferences.getString(this,
            LocalPreferences.AppLoginPreferences.userDesignation
        )
        binding.loginTimeTV.text = LocalPreferences.getString(this,
            LocalPreferences.AppLoginPreferences.loginTime
        )
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)

        //visibility intent values
        when {
            intent.extras?.getBoolean("warehouseKey") == true -> {
                binding.tvHeader.text = "Warehouse"
                binding.warehouseSpinnerCont.gone()
                binding.palletAddBTN.gone()
                binding.rackAddBTN.gone()
                binding.shelfAddBTN.gone()
                binding.warehouseRV.visible()
                screen = "W"
            }
            intent.extras?.getBoolean("rackKey") == true -> {
                binding.tvHeader.text = "Racks"
                binding.businessLocationSpinner.visible()
                binding.rackSpinnerCont.visible()
                binding.warehouseSpinnerCont.visible()
                binding.palletAddBTN.gone()
                binding.whAddBTN.gone()
                binding.shelfAddBTN.gone()
                binding.rackAddBTN.visible()
                binding.racksRV.visible()
                screen = "R"
            }
            intent.extras?.getBoolean("shelfKey") == true -> {
                binding.tvHeader.text = "Shelves"
                binding.businessLocationSpinner.visible()
                binding.warehouseSpinnerCont.visible()
                binding.shelfSpinnerCont.visible()
                binding.rackSpinnerCont.visible()
                binding.palletAddBTN.gone()
                binding.rackAddBTN.gone()
                binding.whAddBTN.gone()
                binding.shelfAddBTN.visible()
                binding.shelfRV.visible()

                screen = "S"
            }
            intent.extras?.getBoolean("palletKey") == true -> {
                binding.tvHeader.text = "Pallets"
                binding.businessLocationSpinner.visible()
                binding.shelfSpinnerCont.visible()
                binding.rackSpinnerCont.visible()
                binding.palletSpinnerCont.visible()
                binding.palletAddBTN.visible()
                binding.rackAddBTN.gone()
                binding.whAddBTN.gone()
                binding.shelfAddBTN.gone()
                screen = "P"
            }
        }

        initListeners()
        setAdapter()
    }

    private fun setAdapter(){
        list = ArrayList()
        warehouseAdapter = WarehouseAdapter(list)
        binding.warehouseRV.apply {
            adapter = warehouseAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }
        rackList = ArrayList()
        racksAdapter = RacksAdapter(rackList)
        binding.racksRV.apply {
            adapter = racksAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }
        shelfList = ArrayList()
        shelfAdapter = ShelfAdapter(shelfList)
        binding.shelfRV.apply {
            adapter = shelfAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }
    }

    private fun initListeners(){
        binding.toolbar.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            clearPreferences(this)
            true
        }

        binding.whAddBTN.click{
            gotoActivity(WarehouseDetailsActivity::class.java)
        }

        binding.rackAddBTN.click {
            gotoActivity(WarehouseDetailsActivity::class.java, racks ,true)
        }

        binding.shelfAddBTN.click {
            gotoActivity(WarehouseDetailsActivity::class.java, shelf ,true)
        }

        binding.palletAddBTN.click {
            gotoActivity(WarehouseDetailsActivity::class.java, pallets ,true)
        }

    }

    private fun showBusLocSpinner(data:List<UserLocationResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val businessLocSpinner = binding.businessLocationSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].busLocationName
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        businessLocSpinner.adapter = adapter

        businessLocSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                Log.i("LocBus","${data[position].orgBusLocNo}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }


    private fun showWarehouseSpinner(data:List<GetWarehouseResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val warehouseSpinner = binding.warehouseSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].wHName
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        warehouseSpinner.adapter = adapter
        warehouseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                data[position].wHCode?.let {
                    LocalPreferences.put(this@BusinessLocationActivity, whNo, data[position].wHNo!!)
                }
                Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }


    private fun showRackSpinner(data:List<GetRackResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val rackSpinner = binding.rackSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].rackName
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        rackSpinner.adapter = adapter

        rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                data[position].rackNo?.let {
                    LocalPreferences.put(this@BusinessLocationActivity, rackNo, it)
                }
                Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }


    private fun showShelfSpinner(data:List<GetShelfResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val shelfResponse = binding.shelfSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].shelfName
            val adapter: ArrayAdapter<String?> =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            //setting adapter to spinner
            shelfResponse.adapter = adapter
            shelfResponse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                    Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }


    private fun clearPreferences(context: Context){
        val settings: SharedPreferences =
            context.getSharedPreferences(LocalPreferences.AppLoginPreferences.PREF, Context.MODE_PRIVATE)
        settings.edit().clear().apply()
        onBackPressed()
    }

}