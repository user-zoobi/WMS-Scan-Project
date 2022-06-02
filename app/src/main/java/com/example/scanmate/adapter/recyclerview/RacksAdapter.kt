package com.example.scanmate.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.data.response.GetRackResponse
import com.example.scanmate.databinding.RacksListViewBinding

class RacksAdapter(val list:ArrayList<GetRackResponse>)  : RecyclerView.Adapter<RacksAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = RacksListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.racks_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){

        }
    }

    override fun getItemCount(): Int = list.size

    fun addItems(listItems:ArrayList<GetRackResponse>){
        list.addAll(listItems)
        notifyDataSetChanged()
    }

}