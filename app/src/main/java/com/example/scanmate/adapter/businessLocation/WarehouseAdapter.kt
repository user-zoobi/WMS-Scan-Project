package com.example.scanmate.adapter.businessLocation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.databinding.WarehouseListViewBinding

class WarehouseAdapter  : RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = WarehouseListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.warehouse_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            when(position){
                0->{
                    binding.wrhTV.text = "Warehouse I"
                }
                1->{
                    binding.wrhTV.text = "Warehouse II"
                }
                2->{
                    binding.wrhTV.text = "Warehouse III"
                }
                3->{
                    binding.wrhTV.text = "Linz Warehouse I"
                }
                4->{
                    binding.wrhTV.text = "Linz Warehouse II"
                }

            }
            binding.editIV.setOnClickListener {
                itemclick?.invoke(position)
            }
            binding.showQRIV.setOnClickListener {
                qrclick?.invoke(position)
            }

        }
    }

    override fun getItemCount(): Int = 8

    var itemclick: ((Int) -> Unit)? = null
    fun onClick(listener: ((Int) -> Unit)) {
        itemclick = listener
    }

    var qrclick: ((Int) -> Unit)? = null
    fun qrOnClick(listener: ((Int) -> Unit)) {
        qrclick = listener
    }

}