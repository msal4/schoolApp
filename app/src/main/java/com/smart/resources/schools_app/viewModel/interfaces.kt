package com.smart.resources.schools_app.viewModel

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.database.model.ScheduleDayModel

interface LoginViewListener{
    fun login()
    fun loginError(errorMsg: String)
}

interface SectionViewModelListener{
    fun onDataDownloaded(adapter:RecyclerView.Adapter<out RecyclerView.ViewHolder>)
    fun onError(errorMsg: String)
    fun onScheduleItemClicked(dayModel: ScheduleDayModel)
}