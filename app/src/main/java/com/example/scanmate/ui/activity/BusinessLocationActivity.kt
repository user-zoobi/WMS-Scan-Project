package com.example.scanmate.ui.activity

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
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LocalPreferences
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
    }

    private fun initObserver(){

        viewModel.userLocation(
            Utils.getSimpleTextBody(LocalPreferences.getInt(this@BusinessLocationActivity, userNo).toString())
        )
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

        viewModel.getWarehouse(WH_Name = "", LocationNo = "1")
        viewModel.getWarehouse.observe(this,Observer{
            when(it.status){
                Status.LOADING->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    it.data?.get(0)?.wHCode?.let { it1 -> Log.i("warehouseResponse", it1) }
                    showWarehouseSpinner(it.data!!)
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
        })

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
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)

        //visibility intent values
        when {
            intent.extras?.getBoolean("warehouseKey") == true ->
            {
                binding.tvHeader.text = "Warehouse"
                binding.warehouseSpinnerCont.visible()
                screen = "W"

            }
            intent.extras?.getBoolean("rackKey") == true ->
            {
                binding.tvHeader.text = "Racks"
                binding.businessLocationSpinner.gone()
                binding.rackSpinnerCont.visible()
                binding.warehouseSpinnerCont.visible()
                screen = "R"
            }
            intent.extras?.getBoolean("shelfKey") == true ->
            {
                binding.tvHeader.text = "Shelves"
                binding.businessLocationSpinner.gone()
                binding.shelfSpinnerCont.visible()
                binding.rackSpinnerCont.visible()
                screen = "S"
            }
            intent.extras?.getBoolean("palletKey") == true ->
            {
                binding.tvHeader.text = "Pallets"
                binding.palletSpinnerCont.visible()
                screen = "P"
            }

        }
        initListeners()
    }

    private fun initListeners(){
        binding.addBTN.click{
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
    }

    private fun showBusLocSpinner(data:List<UserLocationResponse>) {
        //String array to store all the book names
        val items = arrayOfNulls<String>(data.size)
        val businessLocSpinner = binding.businessLocationSpinner

        //Traversing through the whole list to get all the names
        for (i in data.indices) {
            //Storing names to string array
            items[i] = data[i].busLocationName
            businessLocSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, long: Long) {
                    Log.i("LocBus","${adapter?.getItemAtPosition(position)}")
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        businessLocSpinner.adapter = adapter
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