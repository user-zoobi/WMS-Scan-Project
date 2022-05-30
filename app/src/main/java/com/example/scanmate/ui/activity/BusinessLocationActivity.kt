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
    private lateinit var list: ArrayList<UserLocationResponse>

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

        viewModel.getWarehouse.observe(this,Observer{
            when(it.status){
                Status.LOADING->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()

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

        list = ArrayList()
        warehouseAdapter = WarehouseAdapter(list)
        binding.warehouseRV.apply {
            adapter = warehouseAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }

        racksAdapter = RacksAdapter()
        binding.racksRV.apply {
            adapter = racksAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }

        shelfAdapter = ShelfAdapter()
        binding.shelfRV.apply {
            adapter = shelfAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }

        palletAdapter = PalletsAdapter()
        binding.palletsRV.apply {
            adapter = palletAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }

        //visibility intent values

        when
        {
            intent.extras?.getBoolean("warehouseKey") == true ->
            {
                binding.tvHeader.text = "Warehouse"
                screen = "W"

            }
            intent.extras?.getBoolean("rackKey") == true ->
            {
                binding.tvHeader.text = "Racks"
                screen = "R"
            }
            intent.extras?.getBoolean("shelfKey") == true ->
            {
                binding.tvHeader.text = "Shelves"
                screen = "S"
            }
            intent.extras?.getBoolean("palletKey") == true ->
            {
                binding.tvHeader.text = "Pallets"
                screen = "P"
            }

        }

        initListeners()

    }

    private fun initListeners(){

        val businessLocSpinner = binding.businessLocationSpinner
        val wrhSpinner = binding.warehouseSpinner
        val rackSpinner = binding.rackSpinner
        val shelfSpinner = binding.shelfSpinner
        val palletSpinner = binding.palletSpinner

        binding.businessLocationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when(businessLocSpinner.selectedItem.toString()){
                    "BOSCH PLANT I" ->{
                        var boschLoc1 = 1
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc1, boschLoc1)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc1).toString())
                    }
                    "BOSCH PLANT II" ->{
                        var boschLoc2 = 2
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc2, boschLoc2)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc2).toString())
                    }
                    "LINZ PLANT" ->{

                        var boschLoc4 = 4
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc4, boschLoc4)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc4).toString())
                    }
                    "LINZ HEAD OFFICE" ->{

                        var boschLoc5 = 5
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc5, boschLoc5)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc5).toString())
                    }
                    "BOSCH 219" ->{

                        var boschLoc6 = 6
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc6, boschLoc6)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc6).toString())
                    }
                    "BOSCH 15" ->{
                        var boschLoc7 = 7
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc7, boschLoc7)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc7).toString())
                    }
                    "PIP (PVT.) LTD." ->{
                        var boschLoc8 = 8
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc8, boschLoc8)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc8).toString())
                    }
                    "Deutsche Bio-Sys" ->{
                        var boschLoc10 = 10
                        LocalPreferences.put(this@BusinessLocationActivity, LocalPreferences.SpinnerKeys.businessLoc10, boschLoc10)
                        viewModel.getWarehouse("",LocalPreferences.getInt(this@BusinessLocationActivity, businessLoc10).toString())
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when (rackSpinner.selectedItem.toString()){
                    "rack I ( R1 )","rack II ( R2 )","rack III ( R2 )" ->{
                        binding.racksRV.visible()
                    }
                    "Select racks" -> {
                        binding.shelfRV.gone()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.shelfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when (shelfSpinner.selectedItem.toString()){
                    "shelf I","shelf II","shelf III" ->{
                        binding.palletsRV.visible()
                    }
                    "Select shelf" -> {
                        binding.palletsRV.gone()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        palletSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when (palletSpinner.selectedItem.toString()){
                    "pallet I","pallet II","pallet III" ->{
                        binding.palletsRV.visible()
                    }
                    "Select pallet" -> {
                        binding.palletsRV.gone()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        binding.addBTN.click{
            gotoActivity(WarehouseDetailsActivity::class.java)
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

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        //setting adapter to spinner
        businessLocSpinner.adapter = adapter
    }

}