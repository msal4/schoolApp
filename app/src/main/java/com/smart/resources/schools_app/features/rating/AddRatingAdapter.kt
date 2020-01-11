package com.smart.resources.schools_app.features.rating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddRatingBinding
import com.smart.resources.schools_app.databinding.ItemStudentRateBinding

class AddRatingAdapter(private val it: List<RatingModel>, val callback: GetData, val supported:FragmentManager) :
    RecyclerView.Adapter<AddRatingAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemAddRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
/*
        fun bind(ratingModel: RatingModel){
            binding.itemModel=ratingModel
        }*/


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddRatingBinding.inflate(inflater, parent, false)


        binding.rate.setOnClickListener(View.OnClickListener {
            var addRating = AddRatingBottomSheet.newInstance(callback)
            addRating.show(supported, "String")
        })
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int = 25

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.bind(it[position])

    }

}