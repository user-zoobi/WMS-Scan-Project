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

    private var screen = ""

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
                binding.rackSpinner.visible()
            }
            intent.extras?.getBoolean("palletKey") == true ->
            {
                binding.tvHeader.text = "Pallets"
                screen = "P"
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

        binding.businessLocationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when (businessLocSpinner.selectedItem.toString())
                {
                    "Plant I", "Plant II",  "Plant III"->
                    {
                        when (screen)
                        {
                            "W" -> binding.warehouseRV.visible()
                            "R" -> binding.warehouseSpinner.visible()
                        }
                    }
                    "Select business location"->
                    {
                        binding.warehouseRV.gone()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.warehouseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when{
                    wrhSpinner.selectedItem.toString() == "warehouse I"->
                    {
                        binding.racksRV.visible()
                    }
                    wrhSpinner.selectedItem.toString() == "warehouse II"->{
                        binding.racksRV.visible()
                    }
                    wrhSpinner.selectedItem.toString() == "warehouse III"->{
                        binding.racksRV.visible()
                    }
                    wrhSpinner.selectedItem.toString() == "Select warehouse"->{
                        binding.racksRV.gone()
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
                when{
                    rackSpinner.selectedItem.toString() == "rack I ( R1 )"->{
                        binding.shelfRV.visible()
                    }
                    rackSpinner.selectedItem.toString() == "rack II ( R2 )"->{
                        binding.shelfRV.visible()
                    }
                    rackSpinner.selectedItem.toString() == "rack III ( R2 )"->{
                        binding.shelfRV.visible()
                    }
                    rackSpinner.selectedItem.toString() == "Select racks"->{
                        binding.shelfRV.gone()
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.shelfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when{
                    shelfSpinner.selectedItem.toString() == "shelf I"->{
                        binding.palletsRV.visible()
                    }
                    shelfSpinner.selectedItem.toString() == "shelf II"->{
                        binding.palletsRV.visible()
                    }
                    shelfSpinner.selectedItem.toString() == "shelf III"->{
                        binding.palletsRV.visible()
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.palletSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                when{
                    binding.palletSpinner.selectedItem.toString() == "pallet I"->{
                        binding.palletsRV.visible()
                    }
                    binding.palletSpinner.selectedItem.toString() == "pallet II"->{
                        binding.palletsRV.visible()
                    }
                    binding.palletSpinner.selectedItem.toString() == "pallet III"->{
                        binding.palletsRV.visible()
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
}