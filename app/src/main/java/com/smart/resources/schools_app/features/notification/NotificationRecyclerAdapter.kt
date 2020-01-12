package com.smart.resources.schools_app.features.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemNotificationBinding

class NotificationRecyclerAdapter
    : RecyclerView.Adapter<NotificationRecyclerAdapter.MyViewHolder>() {
    private var notifications: List<NotificationModel> = listOf()



    fun updateData(newNotifications: List<NotificationModel>){
        notifications= newNotifications
        notifyDataSetChanged()
    }

    fun clearData(){
        updateData(listOf())
    }

    inner class MyViewHolder(private val binding: ItemNotificationBinding)
        : RecyclerView.ViewHolder(binding.root) {


        fun bind(notificationModel: NotificationModel) {
            binding.itemModel = notificationModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val inflater= LayoutInflater.from(parent.context)
            val binding= ItemNotificationBinding.inflate(inflater, parent, false)
            return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(notifications[position])
    }
}