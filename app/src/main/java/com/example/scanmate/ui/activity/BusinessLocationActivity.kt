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

            }
            intent.extras?.getBoolean("rackKey") == true ->
            {
                binding.tvHeader.text = "Racks"
                binding.businessLocationSpinner.visible()
            }
            intent.extras?.getBoolean("shelfKey") == true ->
            {
                binding.tvHeader.text = "Shelves"
                binding.businessLocationSpinner.visible()
            }
            intent.extras?.getBoolean("palletKey") == true ->
            {
                binding.tvHeader.text = "Pallets"
                binding.businessLocationSpinner.visible()
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

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (businessLocSpinner.selectedItem.toString() == "Plant I")
                {
                    binding.warehouseSpinner.visible()
                }
                else if (businessLocSpinner.selectedItem.toString() == "Select business location")
                {
                    binding.warehouseSpinner.gone()
                    binding.rackSpinner.gone()
                    binding.shelfSpinner.gone()
                    binding.palletSpinner.gone()
                    binding.palletsRV.gone()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.warehouseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (binding.warehouseSpinner.selectedItem.toString() == "warehouse I")
                {
                    binding.rackSpinner.visible()
                }
                else if (binding.warehouseSpinner.selectedItem.toString() == "Select warehouse")
                {
                    binding.rackSpinner.gone()
                    binding.shelfSpinner.gone()
                    binding.palletSpinner.gone()
                    binding.palletsRV.gone()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (binding.rackSpinner.selectedItem.toString() == "rack I ( R1 )" ){
                    binding.shelfSpinner.visible()
                }
                else if (binding.rackSpinner.selectedItem.toString() == "Select racks")
                {
                    binding.shelfSpinner.gone()
                    binding.palletSpinner.gone()
                    binding.palletsRV.gone()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.shelfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (binding.shelfSpinner.selectedItem.toString() == "shelf I" )
                {
                    binding.palletSpinner.visible()
                }
                else if (binding.shelfSpinner.selectedItem.toString() == "Select shelf")
                {
                    binding.palletSpinner.gone()
                    binding.palletsRV.gone()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.palletSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (binding.palletSpinner.selectedItem.toString() == "pallet I" )
                {
                    binding.palletsRV.visible()
                }
                else if (binding.palletSpinner.selectedItem.toString() == "Select pallet" ){
                    binding.palletsRV.gone()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        warehouseAdapter.qrOnClick{
            bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager,"")
        }

        binding.addBTN.click{
            gotoActivity(WarehouseDetailsActivity::class.java)
        }

    }
}