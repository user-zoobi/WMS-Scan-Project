package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanmate.R
import com.example.scanmate.adapter.businessLocation.PalletsAdapter
import com.example.scanmate.adapter.businessLocation.RacksAdapter
import com.example.scanmate.adapter.businessLocation.ShelfAdapter
import com.example.scanmate.adapter.businessLocation.WarehouseAdapter
import com.example.scanmate.storage.data.callback.Status
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.extensions.*
import com.example.scanmate.storage.data.response.UserLocationResponse
import com.example.scanmate.util.CustomProgressDialog
import com.example.scanmate.util.LoginPreferences
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

        Log.i("userDataLocal", LoginPreferences.getInt(this, "userNo").toString())
        initObserver()
    }

    private fun initObserver(){

        val userNo = LoginPreferences.getInt(this, "userNo").toString()
        viewModel.userLocation(Utils.getSimpleTextBody(userNo))

        viewModel.userLoc.observe(this, Observer {
            it.let {
                when (it.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        it.data?.get(0)?.busLocationName?.let { it1 -> Log.i("userDataLocal", it1) }
                        list = it.data as ArrayList<UserLocationResponse>
                        warehouseAdapter.addItems(list)
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }
        })

    }

    private fun setupUi() {

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

        when {
            intent.extras?.getBoolean("warehouseKey") == true -> {
                binding.tvHeader.text = "Warehouse"
                screen = "W"

            }
            intent.extras?.getBoolean("rackKey") == true -> {
                binding.tvHeader.text = "Racks"
                screen = "R"
            }
            intent.extras?.getBoolean("shelfKey") == true -> {
                binding.tvHeader.text = "Shelves"
                screen = "S"
            }
            intent.extras?.getBoolean("palletKey") == true -> {
                binding.tvHeader.text = "Pallets"
                screen = "P"
            }

        }

        initListeners()

    }

    private fun initListeners() {

        val businessLocSpinner = binding.businessLocationSpinner
        val wrhSpinner = binding.warehouseSpinner
        val rackSpinner = binding.rackSpinner
        val shelfSpinner = binding.shelfSpinner
        val palletSpinner = binding.palletSpinner

        binding.businessLocationSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (businessLocSpinner.selectedItem.toString()) {
                        "Plant I", "Plant II", "Plant III" -> {
                            when (screen) {
                                "W" -> binding.warehouseRV.visible()
                                "R" -> binding.rackSpinner.visible()
                                "S" -> binding.shelfSpinner.visible()
                                "P" -> binding.palletSpinner.visible()
                            }
                        }
                        "Select business location" -> {
                            binding.warehouseRV.gone()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (rackSpinner.selectedItem.toString()) {
                    "rack I ( R1 )", "rack II ( R2 )", "rack III ( R2 )" -> {
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

        binding.shelfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (shelfSpinner.selectedItem.toString()) {
                    "shelf I", "shelf II", "shelf III" -> {
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

        palletSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (palletSpinner.selectedItem.toString()) {
                    "pallet I", "pallet II", "pallet III" -> {
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

        binding.addBTN.click {
            gotoActivity(WarehouseDetailsActivity::class.java)
        }

    }
}