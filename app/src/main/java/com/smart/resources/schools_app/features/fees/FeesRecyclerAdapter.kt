package com.smart.resources.schools_app.features.fees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemFeesBinding

class FeesRecyclerAdapter
    : RecyclerView.Adapter<FeesRecyclerAdapter.MyViewHolder>() {
    private var fees: List<FeesDetail> = listOf()



    fun updateData(newFees: List<FeesDetail>){
        fees= newFees
        notifyDataSetChanged()
    }

    fun clearData(){
        updateData(listOf())
    }

    inner class MyViewHolder(private val binding: ItemFeesBinding)
        : RecyclerView.ViewHolder(binding.root) {


        fun bind(feesDetail: FeesDetail) {
            binding.itemModel = feesDetail
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val inflater= LayoutInflater.from(parent.context)
            val binding= ItemFeesBinding.inflate(inflater, parent, false)
            return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = fees.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(fees[position])
    }
}