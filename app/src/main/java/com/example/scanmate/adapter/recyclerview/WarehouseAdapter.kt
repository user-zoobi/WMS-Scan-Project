package com.example.scanmate.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.data.response.GetWarehouseResponse
import com.example.scanmate.databinding.WarehouseListViewBinding
import com.example.scanmate.data.response.UserLocationResponse

class WarehouseAdapter(
    private val list:ArrayList<GetWarehouseResponse>
    )  : RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = WarehouseListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.warehouse_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        with(holder){
           binding.wrhTV.text = data.wHName
           binding.wrhNo.text = data.wHNo.toString()
        }
    }

    override fun getItemCount(): Int = list.size

    /*fun addItems(listItems:ArrayList<GetWarehouseResponse>){
        list.addAll(listItems)
        notifyDataSetChanged()
    }*/

}