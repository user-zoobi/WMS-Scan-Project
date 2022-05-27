package com.example.scanmate.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.databinding.RacksListViewBinding

class RacksAdapter  : RecyclerView.Adapter<RacksAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = RacksListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.racks_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            when(position){
                0->{
                    binding.rackTV.text = "Rack I"
                }
                1->{
                    binding.rackTV.text = "Rack II"
                }
                2->{
                    binding.rackTV.text = "Rack III"
                }
                3->{
                    binding.rackTV.text = "Rack IV"
                }
                4->{
                    binding.rackTV.text = "Rack V"
                }

            }
            binding.editIV.setOnClickListener {
                itemclick?.invoke(position)
            }

        }
    }

    override fun getItemCount(): Int = 8

    var itemclick: ((Int) -> Unit)? = null
    fun onClick(listener: ((Int) -> Unit)) {
        itemclick = listener
    }

}