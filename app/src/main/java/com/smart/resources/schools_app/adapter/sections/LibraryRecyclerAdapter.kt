package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.database.model.LibraryModel
import com.smart.resources.schools_app.databinding.ItemLibraryBinding

class LibraryRecyclerAdapter(private val listLib: List<LibraryModel>) : RecyclerView.Adapter<LibraryRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemLibraryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(libraryModel: LibraryModel){
            binding.itemModel=libraryModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemLibraryBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listLib.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listLib[position])
    }

}