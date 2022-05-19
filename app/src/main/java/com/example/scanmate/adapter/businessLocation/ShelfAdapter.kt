package com.example.scanmate.adapter.businessLocation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.databinding.ShelfListViewBinding

class ShelfAdapter  : RecyclerView.Adapter<ShelfAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ShelfListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shelf_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            when(position){
                0->{
                    binding.shelfTV.text = "Shelf I"
                }
                1->{
                    binding.shelfTV.text = "Shelf  II"
                }
                2->{
                    binding.shelfTV.text = "Shelf  III"
                }
                3->{
                    binding.shelfTV.text = "Shelf  IV"
                }
                4->{
                    binding.shelfTV.text = "Shelf  V"
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