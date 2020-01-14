package com.smart.resources.schools_app.features.rating.addRarting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddRatingBinding
import com.smart.resources.schools_app.features.rating.AddRatingModel

class AddRatingAdapter(private val onItemClicked: (AddRatingModel, Int) -> Unit) :
    RecyclerView.Adapter<AddRatingAdapter.MyViewHolder>() {
    private var ratingModels: MutableList<AddRatingModel>? = null

    fun updateData(ratingModels: MutableList<AddRatingModel>) {
        this.ratingModels = ratingModels
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, addRatingModel:AddRatingModel) {
        ratingModels?.also {
            it[position] = addRatingModel
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        ratingModels = null
        notifyDataSetChanged()
    }

    fun resetAll() {
        ratingModels?.apply {
            forEach { it.reset() }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddRatingBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int = ratingModels?.size ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        ratingModels?.get(position)?.let { ratingModel ->
            holder.bind(ratingModel)
            holder.binding.root.setOnClickListener {
                onItemClicked(ratingModel, position)
            }
        }
    }

    inner class MyViewHolder(val binding: ItemAddRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ratingModel: AddRatingModel) {
            binding.ratingModel = ratingModel
        }
    }
}