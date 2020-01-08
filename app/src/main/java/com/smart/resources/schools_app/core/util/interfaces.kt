package com.smart.resources.schools_app.core.util

import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.features.schedule.ScheduleDayModel


interface SectionViewModelListener{
    fun onDataDownloaded(adapter:RecyclerView.Adapter<out RecyclerView.ViewHolder>)
    fun onError(errorMsg: String)
}