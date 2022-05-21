package com.example.scanmate.adapter.businessLocation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanmate.R
import com.example.scanmate.databinding.PalletListViewBinding
import com.example.scanmate.extensions.click

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
            binding.showQRIV.setOnClickListener {
                palletItemClick?.invoke(position)
            }

            binding.editIV.click {
                palletEditItem?.invoke(position)
            }

        }
    }

    override fun getItemCount(): Int = 8

    private var palletItemClick: ((Int) -> Unit)? = null
    fun palletQRClick(listener: ((Int) -> Unit)) {
        palletItemClick = listener
    }

    private var palletEditItem: ((Int) -> Unit)? = null
    fun palletEditClick(listener: ((Int) -> Unit)) {
        palletEditItem = listener
    }


}