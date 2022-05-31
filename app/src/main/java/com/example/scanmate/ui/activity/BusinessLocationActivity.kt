package com.example.scanmate.ui.activity

import android.content.Intent
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
import com.example.scanmate.util.LocalPreferences.AppLoginPreferences.userNo
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

    private var screen = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = CustomProgressDialog(this)
        setupUi()
        viewModel = obtainViewModel(MainViewModel::class.java)
        initObserver()
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
            Utils.getSimpleTextBody("3"),
            Utils.getSimpleTextBody("1"),
        )
        viewModel.getRack.observe(this, Observer{
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    showRackSpinner(it.data!!)
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
            Utils.getSimpleTextBody("3"),
            Utils.getSimpleTextBody("1"),
        )
        viewModel.getShelf.observe(this,Observer{
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    showShelfSpinner(it.data!!)
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
            intent.extras?.getBoolean("warehouseKey") == true ->
            {
                binding.tvHeader.text = "Warehouse"
                binding.warehouseSpinnerCont.gone()
                binding.palletAddBTN.gone()
                binding.rackAddBTN.gone()
                binding.shelfAddBTN.gone()
                binding.warehouseRV.visible()
                screen = "W"
            }
            intent.extras?.getBoolean("rackKey") == true ->
            {
                binding.tvHeader.text = "Racks"
                binding.businessLocationSpinner.gone()
                binding.rackSpinnerCont.visible()
                binding.warehouseSpinnerCont.visible()
                binding.palletAddBTN.gone()
                binding.whAddBTN.gone()
                binding.shelfAddBTN.gone()
                binding.rackAddBTN.visible()
                screen = "R"
            }
            intent.extras?.getBoolean("shelfKey") == true ->
            {
                binding.tvHeader.text = "Shelves"
                binding.businessLocationSpinner.gone()
                binding.shelfSpinnerCont.visible()
                binding.rackSpinnerCont.visible()
                binding.palletAddBTN.gone()
                binding.rackAddBTN.gone()
                binding.whAddBTN.gone()
                binding.shelfAddBTN.visible()

                screen = "S"
            }
            intent.extras?.getBoolean("palletKey") == true ->
            {
                binding.tvHeader.text = "Pallets"
                binding.palletSpinnerCont.visible()
                binding.palletAddBTN.visible()
                binding.rackAddBTN.gone()
                binding.whAddBTN.gone()
                binding.shelfAddBTN.gone()
                screen = "P"
            }

        }
        initListeners()
    }

    private fun initListeners(){
        binding.whAddBTN.click{
            gotoActivity(WarehouseDetailsActivity::class.java)
        }
        val businessLocSpinner = binding.businessLocationSpinner
        val wrhSpinner = binding.warehouseSpinner
        val rackSpinner = binding.rackSpinner
        val shelfSpinner = binding.shelfSpinner
        val palletSpinner = binding.palletSpinner

        businessLocSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, long: Long) {
                Log.i("response", adapterView?.getItemAtPosition(position).toString())
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
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
            warehouseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                    Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        warehouseSpinner.adapter = adapter
    }


    private fun showRackSpinner(data:List<GetRackResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val rackSpinner = binding.rackSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].rackName
            rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                    Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        rackSpinner.adapter = adapter
    }


    private fun showShelfSpinner(data:List<GetShelfResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val shelfResponse = binding.shelfSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].shelfName
            shelfResponse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                    Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        shelfResponse.adapter = adapter
    }

}