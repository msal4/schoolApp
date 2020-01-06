package com.smart.resources.schools_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemExamBinding
import com.smart.resources.schools_app.databinding.ItemScheduleDayBinding

class ScheduleDayRecyclerAdapter(private val daySchedule:List<String?>) : RecyclerView.Adapter<ScheduleDayRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemScheduleDayBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemScheduleDayBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = daySchedule.filter {!it.isNullOrEmpty()}.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            subjectName.text = daySchedule[position]
            itemPos.text= (position+1).toString()
        }
    }

}