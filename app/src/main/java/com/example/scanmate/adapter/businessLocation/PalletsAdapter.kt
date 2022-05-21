package com.example.scanmate.adapter.businessLocation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.databinding.PalletListViewBinding

class PalletsAdapter : RecyclerView.Adapter<PalletsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = PalletListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pallet_list_view, parent, false)
        return ViewHolder(view)
    }
    //
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            when(position){
                0->{
                    binding.palletTV.text = "Shelf I"
                }
                1->{
                    binding.palletTV.text = "Shelf  II"
                }
                2->{
                    binding.palletTV.text = "Shelf  III"
                }
                3->{
                    binding.palletTV.text = "Shelf  IV"
                }
                4->{
                    binding.palletTV.text = "Shelf  V"
                }

            }
            binding.editIV.setOnClickListener {
                palletItemClick?.invoke(position)
            }

        }
    }

    override fun getItemCount(): Int = 8

    var palletItemClick: ((Int) -> Unit)? = null
    fun palletOnClick(listener: ((Int) -> Unit)) {
        palletItemClick = listener
    }
}