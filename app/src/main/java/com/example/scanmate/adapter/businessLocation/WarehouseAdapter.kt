package com.example.scanmate.adapter.businessLocation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.databinding.WarehouseListViewBinding
import com.example.scanmate.storage.data.response.UserLocationResponse

class WarehouseAdapter(
    private val list:ArrayList<UserLocationResponse>
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
           binding.wrhTV.text = data.busLocationName
        }
    }

    override fun getItemCount(): Int = list.size

    var itemclick: ((Int) -> Unit)? = null
    fun onClick(listener: ((Int) -> Unit)) {
        itemclick = listener
    }

    var qrclick: ((Int) -> Unit)? = null
    fun qrOnClick(listener: ((Int) -> Unit)) {
        qrclick = listener
    }

    fun addItems(listItems:ArrayList<UserLocationResponse>){
        list.addAll(listItems)
        notifyDataSetChanged()
    }

}