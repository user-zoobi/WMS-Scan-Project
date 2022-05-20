package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanmate.R
import com.example.scanmate.adapter.businessLocation.PalletsAdapter
import com.example.scanmate.adapter.businessLocation.RacksAdapter
import com.example.scanmate.adapter.businessLocation.ShelfAdapter
import com.example.scanmate.adapter.businessLocation.WarehouseAdapter
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.extensions.*
import com.example.scanmate.ui.bottomsheets.BottomSheetFragment

class BusinessLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessLocationBinding
    private lateinit var warehouseAdapter: WarehouseAdapter
    private lateinit var racksAdapter: RacksAdapter
    private lateinit var shelfAdapter: ShelfAdapter
    private lateinit var palletAdapter: PalletsAdapter
    private lateinit var bottomSheet: BottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)
        setupUi()
    }

    private fun setupUi(){

        warehouseAdapter = WarehouseAdapter()
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
        binding.shelfRV.apply {
            adapter = palletAdapter
            layoutManager = LinearLayoutManager(this@BusinessLocationActivity)
        }

        //visibility intent values

        when
        {
            intent.extras?.getBoolean("warehouseKey") == true ->
            {
                binding.tvHeader.text = "Warehouse"
            }
            intent.extras?.getBoolean("rackKey") == true ->
            {
                binding.tvHeader.text = "Racks"
                binding.businessLocationSpinner.gone()
                binding.warehouseSpinner.visible()
            }
            intent.extras?.getBoolean("shelfKey") == true ->
            {
                binding.tvHeader.text = "Shelves"
                binding.businessLocationSpinner.gone()
                binding.rackSpinner.visible()
            }
            intent.extras?.getBoolean("palletKey") == true ->
            {
                binding.tvHeader.text = "Pallets"
                binding.businessLocationSpinner.gone()
                binding.shelfSpinner.visible()
            }

        }

        initListeners()

    }

    private fun initListeners(){

        val businessLocSpinner = binding.businessLocationSpinner
        val wrhSpinner = binding.warehouseSpinner
        val rackSpinner = binding.rackSpinner
        val shelfSpinner = binding.shelfSpinner

        binding.businessLocationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (businessLocSpinner.selectedItem.toString() == "Plant I"){
                    binding.warehouseRV.visible()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.warehouseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (binding.warehouseSpinner.selectedItem.toString() == "warehouse I"){
                    binding.racksRV.visible()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (binding.rackSpinner.selectedItem.toString() == "rack I ( R1 )" ){
                    binding.shelfRV.visible()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        warehouseAdapter.onClick {
//            gotoActivity(WarehouseDetailsActivity::class.java)
        }

        warehouseAdapter.qrOnClick {
            bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager,"")
        }


    }
}