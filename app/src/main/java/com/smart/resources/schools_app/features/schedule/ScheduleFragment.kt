package com.smart.resources.schools_app.features.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.extentions.createGridLayout
import com.smart.resources.schools_app.core.myTypes.WeekDays
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private val viewModel: ScheduleViewModel by viewModels()

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
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@ScheduleFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.schedule)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.apply {
                binding.listState= listState
                getSchedule().observe(viewLifecycleOwner) {onScheduleDownloaded(it)}
        }
    }

    private  fun onScheduleDownloaded(result: List<List<String?>>) {
        setupWeekRecycler(result)

    }


    private fun setupWeekRecycler(result:List<List<String?>>) =
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

    private fun mapWeekRecyclerData(result: List<List<String?>>) =
        (result).run {
            val daysPerWeek= 7
            if(size > daysPerWeek) subList(0, daysPerWeek) else this
        }
            .mapIndexed { index, list ->
                ScheduleDayModel(
                    WeekDays.getDayName(index),
                    list
                )
            }
             // filter list with empty strings
            .filter {!it.dayList.filter { item-> !item.isNullOrBlank()}.isNullOrEmpty()}
}
