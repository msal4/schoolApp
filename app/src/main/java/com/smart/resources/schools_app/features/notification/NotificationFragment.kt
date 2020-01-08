package com.smart.resources.schools_app.features.notification

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.ContainerActivities.SectionActivity
import com.smart.resources.schools_app.core.util.*

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: NotificationRecyclerAdapter
    private lateinit var viewModel:NotificationViewModel

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                NotificationFragment()

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
        (activity as SectionActivity).setCustomTitle(R.string.notifications)

        setupViewModel()
        setupAdapter()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(NotificationViewModel::class.java).apply {
                getNotifications(NotificationType.STUDENT)
                    .observe(this@NotificationFragment, Observer{onNotificationsDownloaded(it)})
            }
    }

    private fun setupAdapter() {
        NotificationRecyclerAdapter()
            .apply {
                adapter = this
                onTabClicked = onTabClicked()
                binding.recyclerView.adapter = this
            }
    }

    private fun onTabClicked(): (NotificationType) -> Unit {
        return {
            adapter.clearData()
            binding.errorText.text = ""
            binding.progressIndicator.show()
            viewModel.fetchNotifications(it)
        }
    }

    private  fun onNotificationsDownloaded(result: MyResult<List<NotificationModel>>) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_notifications)
                else adapter.updateData(result.data)

            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

        binding.errorText.text = errorMsg
        binding.progressIndicator.hide()
    }
}
