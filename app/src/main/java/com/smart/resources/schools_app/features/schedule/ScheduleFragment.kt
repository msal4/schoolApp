package com.smart.resources.schools_app.features.schedule

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.containerActivities.SectionActivity
import com.smart.resources.schools_app.core.util.*

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: ScheduleViewModel

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                ScheduleFragment()

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
        (activity as SectionActivity).setCustomTitle(R.string.schedule)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(ScheduleViewModel::class.java).apply {
                getSchedule().observe(this@ScheduleFragment, Observer{onScheduleDownloaded(it)})
            }
    }

    private  fun onScheduleDownloaded(result: ScheduleResult) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_schedule)
                else {
                    setupWeekRecycler(result)
                }
            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

        binding.errorText.text = errorMsg
        binding.progressIndicator.hide()
    }


    private fun setupWeekRecycler(result: Success<List<Any>>) =
        binding
            .recyclerView
            .createGridLayout(
                WeekRecyclerAdapter(mapWeekRecyclerData(result))
                    .apply{onClick = ::onDayClicked })


    private fun onDayClicked(scheduleDayModel: ScheduleDayModel){
        fragmentManager?.let {
            ScheduleDayFragment.newInstance(
                it,
                scheduleDayModel
            )
        }
    }

    private fun mapWeekRecyclerData(result: Success<List<Any>>) =
        (result.data as List<List<String?>>)
            .mapIndexed { index, list ->
                ScheduleDayModel(
                    WeekDays.getDayName(index),
                    list
                )
            }
            .filter {!it.dayList.isNullOrEmpty()}
}
