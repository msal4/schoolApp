package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.smart.resources.schools_app.database.model.NotificationModel
import com.smart.resources.schools_app.databinding.ItemNotificationBinding
import com.smart.resources.schools_app.databinding.ItemTabsBinding
import com.smart.resources.schools_app.util.NotificationType

class NotificationRecyclerAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onTabClicked: ((NotificationType)-> Unit)? = null
    private var notifications: List<NotificationModel> = listOf()


    companion object{
        private const val TABS_VIEW_TYPE= 0
        private const val NORMAL_VIEW_TYPE= 1
    }

    fun updateData(newNotifications: List<NotificationModel>){
        notifications= newNotifications
        notifyDataSetChanged()
    }

    fun clearData(){
        updateData(listOf())
    }

    inner class NormalViewHolder(private val binding: ItemNotificationBinding)
        : RecyclerView.ViewHolder(binding.root) {


        fun bind(notificationModel: NotificationModel) {
            binding.itemModel = notificationModel
        }
    }

    inner class TabsViewHolder(val binding: ItemTabsBinding)
        : RecyclerView.ViewHolder(binding.root){
        init {

            binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabReselected(p0: TabLayout.Tab) {
                }

                override fun onTabUnselected(p0: TabLayout.Tab) {
                }

                override fun onTabSelected(tab: TabLayout.Tab) {
                        onTabClicked?.invoke(
                            if(tab.position == 0) NotificationType.STUDENT
                            else NotificationType.SECTION)
                }
            })
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        val inflater= LayoutInflater.from(parent.context)

        return if(viewType== TABS_VIEW_TYPE){
            val binding= ItemTabsBinding.inflate(inflater, parent, false)
            TabsViewHolder(binding)
        }else{
            val binding= ItemNotificationBinding.inflate(inflater, parent, false)
            NormalViewHolder(binding)
        }

    }

    override fun getItemCount(): Int = notifications.size + 1

    override fun getItemViewType(position: Int): Int=
        if(position== 0) TABS_VIEW_TYPE else NORMAL_VIEW_TYPE

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NormalViewHolder) {
            holder.bind(notifications[position-1])
        }
    }
}