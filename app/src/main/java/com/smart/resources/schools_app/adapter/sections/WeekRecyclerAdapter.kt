package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemWeekDayBinding
import com.smart.resources.schools_app.util.WeekDays

class WeekRecyclerAdapter(private val workDays: List<Int>) : RecyclerView.Adapter<WeekRecyclerAdapter.MyViewHolder>() {
    private var listener: ItemListener? = null

    interface ItemListener {
        fun onItemClick(dayOfWeek: WeekDays)
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }

    inner class MyViewHolder(private val binding: ItemWeekDayBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weekDays:WeekDays){
            binding.dayOfWeek= weekDays
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemWeekDayBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = WeekDays.values().size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(workDays.contains(position)) {
            val dayOfWeek= WeekDays.values()[position]
            holder.apply {
                bind(dayOfWeek)

                itemView
                .setOnClickListener {
                    listener?.onItemClick(dayOfWeek)
                }
            }
        }
    }

}