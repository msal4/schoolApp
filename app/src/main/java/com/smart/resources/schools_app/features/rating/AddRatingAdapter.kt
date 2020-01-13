package com.smart.resources.schools_app.features.rating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddRatingBinding
import com.smart.resources.schools_app.databinding.ItemStudentRateBinding
import com.smart.resources.schools_app.features.exam.ExamModel
import com.smart.resources.schools_app.features.exam.ExamRecyclerAdapter
import com.smart.resources.schools_app.features.profile.StudentInfoModel
import com.smart.resources.schools_app.features.students.Student

class AddRatingAdapter(private val it: List<Student>,private val listener: AddRatingAdapter.OnItemClickListener) :
    RecyclerView.Adapter<AddRatingAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(examModel: Student)
    }
    inner class MyViewHolder(val binding: ItemAddRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ratingModel: Student){
            binding.itemModel=ratingModel
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


    override fun getItemCount(): Int = it.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val rating = it[position]
        holder.itemView.setOnClickListener { listener.onItemClick(rating) }


        holder.bind(rating)

    }

}