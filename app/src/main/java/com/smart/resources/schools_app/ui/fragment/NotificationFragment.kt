package com.smart.resources.schools_app.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.sections.NotificationRecyclerAdapter
import com.smart.resources.schools_app.database.dao.NotificationsDao
import com.smart.resources.schools_app.database.model.NotificationModel
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.ui.activity.SectionActivity
import com.smart.resources.schools_app.util.*
import kotlinx.android.synthetic.main.fragment_recycler_loader.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var notificationsDao: NotificationsDao
    private lateinit var adapter: NotificationRecyclerAdapter

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=  NotificationFragment()

            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false)
        adapter=  NotificationRecyclerAdapter()
        adapter.onTabClicked={
            adapter.clearData()
            binding.errorText.text= ""
            if(it== NotificationType.STUDENT){
                fetchStudentNotifications()
            }else{
                fetchSectionNotifications()
            }
        }
        binding.recyclerView.adapter=adapter


        notificationsDao= BackendHelper
            .retrofitWithAuth
            .create(NotificationsDao::class.java)

        fetchStudentNotifications()

        (activity as SectionActivity).setCustomTitle(R.string.notifications)
        return binding.root
    }

    fun fetchStudentNotifications() = CoroutineScope(IO).launch {
        withContext(Main){binding.progressIndicator.show()}


        val result= MyResult
            .fromResponse(GlobalScope.async {notificationsDao.fetchNotifications()})

        setupAdapter(result)
    }

    fun fetchSectionNotifications() = CoroutineScope(IO).launch {
        withContext(Main){binding.progressIndicator.show()}

        val result= MyResult
            .fromResponse(GlobalScope.async {notificationsDao.fetchSectionNotifications()})

        setupAdapter(result)
    }

    private suspend fun setupAdapter(result: MyResult<List<NotificationModel>>) =
        withContext(Main){
            var errorMsg= ""
            when(result){
                is Success -> {
                    if(result.data.isNullOrEmpty()) errorMsg= getString(R.string.no_notifications)
                    else adapter.updateData(result.data)

                }
                is ResponseError -> errorMsg= result.combinedMsg
                is ConnectionError -> errorMsg=  getString(R.string.connection_error)
            }

            binding.errorText.text= errorMsg
            binding.progressIndicator.hide()
        }


}
