package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.database.model.ScheduleDayModel
import com.smart.resources.schools_app.databinding.ItemWeekDayBinding
import com.smart.resources.schools_app.util.WeekDays

class WeekRecyclerAdapter(private val schedule: List<ScheduleDayModel>)
    : RecyclerView.Adapter<WeekRecyclerAdapter.MyViewHolder>() {
    var onClick: ((ScheduleDayModel)->Unit)?= null

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

    override fun getItemCount(): Int = schedule.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val dayOfWeek= WeekDays.values()[position]
            val scheduleDay= schedule[position]

            holder.apply {
                bind(dayOfWeek)
                itemView
                .setOnClickListener {
                    onClick?.invoke(scheduleDay)
                }
            }
    }

}