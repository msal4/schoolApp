package com.smart.resources.schools_app.features.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemWeekDayBinding
import com.smart.resources.schools_app.core.myTypes.WeekDays

class WeekRecyclerAdapter(private val schedule: List<ScheduleDayModel>)
    : RecyclerView.Adapter<WeekRecyclerAdapter.MyViewHolder>() {
    var onClick: ((ScheduleDayModel)->Unit)?= null

    inner class MyViewHolder(val binding: ItemWeekDayBinding) : RecyclerView.ViewHolder(binding.root) {

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

            val scheduleDay= schedule[position]

            holder.binding.apply {
                dayOfWeek.text= scheduleDay.day

                root.setOnClickListener {
                    onClick?.invoke(scheduleDay)
                }
            }
    }

}