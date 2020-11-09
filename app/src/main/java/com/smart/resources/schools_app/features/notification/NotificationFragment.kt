package com.smart.resources.schools_app.features.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.myTypes.NotificationType
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var adapter: NotificationRecyclerAdapter
    private val viewModel: NotificationViewModel by viewModels()

    companion object {
        fun newInstance(fm: FragmentManager) {

            val fragment =
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
        binding = FragmentNotificationsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@NotificationFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.notifications)

        setupViewModel()
        setupAdapter()
        setupTabs()
        return binding.root
    }

    private fun setupTabs() {
        viewModel.userType.observe(viewLifecycleOwner) {
            if (it == UserType.TEACHER) {
                binding.tabs.visibility = View.GONE
            } else {
                binding.tabs.addOnTabSelectedListener(object :
                    TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(p0: TabLayout.Tab) {
                    }

                    override fun onTabUnselected(p0: TabLayout.Tab) {
                    }

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        onTabClicked(
                            if (tab.position == 0) NotificationType.STUDENT
                            else NotificationType.SECTION
                        )
                    }
                })
            }
        }

    }

    private fun setupViewModel() {
        viewModel.apply {
            binding.recyclerViewLoader.listState = listState
            notifications.observe(viewLifecycleOwner) { onNotificationsDownloaded(it) }
        }
    }

    private fun setupAdapter() {
        NotificationRecyclerAdapter()
            .apply {
                adapter = this
                binding.recyclerViewLoader.recyclerView.adapter = this
            }
    }

    private fun onTabClicked(type: NotificationType) {
        adapter.clearData()
        viewModel.notificationType.value = type

    }

    private fun onNotificationsDownloaded(result: List<NotificationModel>) {
        adapter.updateData(result)
    }
}
