package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemScheduleBinding

class ScheduleRecyclerAdapter : RecyclerView.Adapter<ScheduleRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemScheduleBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 25

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

}