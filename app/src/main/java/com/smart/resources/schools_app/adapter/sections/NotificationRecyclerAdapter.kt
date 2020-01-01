package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.database.model.ExamModel
import com.smart.resources.schools_app.database.model.LibraryModel
import com.smart.resources.schools_app.database.model.NotificationsModel
import com.smart.resources.schools_app.databinding.ItemNotificationBinding
import com.smart.resources.schools_app.databinding.ItemTabsBinding

class NotificationRecyclerAdapter(private val data: List<NotificationsModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TABS_VIEW_TYPE= 0
        private const val NORMAL_VIEW_TYPE= 1
    }

    inner class NormalViewHolder(private val binding: ItemNotificationBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(notificationsModel: NotificationsModel) {
            binding.itemModel = notificationsModel
        }
    }

    inner class TabsViewHolder(binding: ItemTabsBinding)
        : RecyclerView.ViewHolder(binding.root)

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

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int=
        if(position== 0) TABS_VIEW_TYPE else NORMAL_VIEW_TYPE

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NormalViewHolder) {
            holder.bind(data[position])
        }
    }
}