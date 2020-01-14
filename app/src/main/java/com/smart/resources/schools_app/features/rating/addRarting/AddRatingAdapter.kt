package com.smart.resources.schools_app.features.rating.addRarting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddRatingBinding
import com.smart.resources.schools_app.features.rating.RatingModel

class AddRatingAdapter(private val onItemClicked: (RatingModel, Int) -> Unit) :
    RecyclerView.Adapter<AddRatingAdapter.MyViewHolder>() {
    private var ratingModels: MutableList<RatingModel>? = null

    fun updateData(ratingModels: MutableList<RatingModel>) {
        this.ratingModels = ratingModels
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, ratingModel:RatingModel) {
        ratingModels?.also {
            it[position] = ratingModel
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

        fun bind(ratingModel: RatingModel) {
            binding.ratingModel = ratingModel
        }
    }
}