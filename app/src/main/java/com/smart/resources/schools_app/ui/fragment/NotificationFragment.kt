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

        NotificationRecyclerAdapter().apply {
            adapter= this
            onTabClicked= onTabClicked()
            binding.recyclerView.adapter=this
        }


        notificationsDao= BackendHelper
            .retrofitWithAuth
            .create(NotificationsDao::class.java)

        fetchNotifications(NotificationType.STUDENT)

        (activity as SectionActivity).setCustomTitle(R.string.notifications)
        return binding.root
    }

    private fun onTabClicked(): (NotificationType) -> Unit {
        return {
            adapter.clearData()
            binding.errorText.text = ""
            fetchNotifications(it)
        }
    }

    private fun fetchNotifications(notificationType: NotificationType) =
        CoroutineScope(IO)
            .launch {
                 withContext(Main){binding.progressIndicator.show()}
                    val result= MyResult
                         .fromResponse(GlobalScope.async { getNotifications(notificationType) })
            setupAdapter(result)
    }

    private suspend fun getNotifications(notificationType: NotificationType) =
        if (notificationType == NotificationType.STUDENT) notificationsDao.fetchNotifications()
        else notificationsDao.fetchSectionNotifications()


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
