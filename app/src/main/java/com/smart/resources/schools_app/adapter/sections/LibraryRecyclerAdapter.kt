package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemLibraryBinding

class LibraryRecyclerAdapter : RecyclerView.Adapter<LibraryRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemLibraryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemLibraryBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 25

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

}