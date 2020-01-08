package com.smart.resources.schools_app.features.schedule

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentScheduleDayBinding
import com.smart.resources.schools_app.features.ContainerActivities.SectionActivity

class ScheduleDayFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDayBinding
    private var dayModel: ScheduleDayModel?= null

    companion object {
        private const val ADD_EXAM_FRAGMENT= "scheduleDayFragment"
        private const val EXTRA_SCHEDULE_DAY= "extraScheduleDay"

        fun newInstance(fm:FragmentManager, scheduleDay: ScheduleDayModel){

            val fragment=
                ScheduleDayFragment()

            Bundle().apply {
                putParcelable(EXTRA_SCHEDULE_DAY, scheduleDay)
                fragment.arguments=  this
            }

            fm.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_EXAM_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleDayBinding.inflate(inflater, container, false)

        dayModel= arguments?.getParcelable(EXTRA_SCHEDULE_DAY)

        dayModel?.apply {
            (activity as SectionActivity).setCustomTitle(day)
            binding.recyclerView.adapter= this.dayList?.let {
                ScheduleDayRecyclerAdapter(
                    it
                )
            }
        }

        return binding.root
    }
}
