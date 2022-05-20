package com.example.scanmate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.scanmate.R
import com.example.scanmate.databinding.ActivityBusinessLocationBinding
import com.example.scanmate.databinding.ActivityCreateCartonBinding
import com.example.scanmate.databinding.ActivityLoginBinding
import com.example.scanmate.extensions.gone
import com.example.scanmate.extensions.setTransparentStatusBarColor
import com.example.scanmate.extensions.toast
import com.example.scanmate.extensions.visible

class CreateCartonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCartonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCartonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTransparentStatusBarColor(R.color.transparent)

        initListener()

    }

    private fun initListener(){

        binding.createCartonBtn.setOnClickListener {
            toast("Data Saved")
        }

        binding.businessLocationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val businessLocation: String = binding.businessLocationSpinner.selectedItem.toString()
                Log.i("businessLoc", businessLocation)

                if (binding.businessLocationSpinner.selectedItem == "Plant I"){
                    binding.warehouseSpinner.visible()
                }

                if (binding.businessLocationSpinner.selectedItem == "Select business location"){
                    binding.warehouseSpinner.gone()
                    binding.rackSpinner.gone()
                    binding.shelfSpinner.gone()
                    binding.palletSpinner.gone()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.warehouseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val businessLocation: String = binding.warehouseSpinner.selectedItem.toString()
                Log.i("businessLoc", businessLocation)
                if (binding.warehouseSpinner.selectedItem == "warehouse I"){
                    binding.rackSpinner.visible()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.rackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val businessLocation: String = binding.warehouseSpinner.selectedItem.toString()
                Log.i("businessLoc", businessLocation)
                if (binding.rackSpinner.selectedItem == "rack I ( R1 )"){
                    binding.shelfSpinner.visible()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.shelfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (binding.shelfSpinner.selectedItem == "shelf I"){
                    binding.palletSpinner.visible()

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }
}