package com.smart.resources.schools_app.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.sections.LibraryRecyclerAdapter
import com.smart.resources.schools_app.adapter.sections.WeekRecyclerAdapter
import com.smart.resources.schools_app.database.model.ScheduleDayModel
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.ui.activity.SectionActivity
import com.smart.resources.schools_app.util.Section
import com.smart.resources.schools_app.util.SharedPrefHelper
import com.smart.resources.schools_app.util.UserType
import com.smart.resources.schools_app.util.hide
import com.smart.resources.schools_app.viewModel.SectionViewModel
import com.smart.resources.schools_app.viewModel.SectionViewModelFactory
import com.smart.resources.schools_app.viewModel.SectionViewModelListener


class SectionFragment : Fragment(), SectionViewModelListener {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var section:Section

    companion object {
        private const val EXTRA_SECTION = "extraSection"

        fun newInstance(fm: FragmentManager, section: Section) {
            val fragment = SectionFragment()
            Bundle().apply {
                putSerializable(EXTRA_SECTION, section)
                fragment.arguments = this
            }

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
        // Inflate the layout for this fragment
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        section= arguments?.getSerializable(EXTRA_SECTION) as Section


        ViewModelProviders
            .of(this,
                SectionViewModelFactory(
                    activity!!.application,
                    section,
            this))
            .get(SectionViewModel::class.java).apply {
                downloadData()
            }

        setActivityTitle()
        return binding.root
    }

    private fun setActivityTitle() {
        val stringId=when (section) {
            Section.HOMEWORK -> R.string.homework
            Section.EXAM -> R.string.exams
            Section.LIBRARY ->R.string.library
            Section.SCHEDULE -> R.string.schedule
            Section.ABSENCE -> R.string.absence
        }
        (activity as SectionActivity).setCustomTitle(stringId)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(shouldShowAdd()) {
            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun shouldShowAdd(): Boolean {
        return (section == Section.HOMEWORK ||
                section == Section.EXAM ||
                section == Section.ABSENCE) && SharedPrefHelper.instance?.userType == UserType.TEACHER
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMenuItem-> gotoAddFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotoAddFragment() {
        when (section) {
            Section.HOMEWORK -> {
                fragmentManager?.let { AddHomeworkFragment.newInstance(it) }
            }
            Section.EXAM -> {
                fragmentManager?.let { AddExamFragment.newInstance(it) }
            }
            Section.ABSENCE -> {
                fragmentManager?.let { AddAbsenceFragment.newInstance(it) }
            }
        }
    }

    private fun createGridLayout(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        binding.recyclerView.apply {
            val itemMargin = resources.getDimension(R.dimen.item_margin).toInt()
            setPadding(itemMargin, itemMargin, 0, itemMargin)
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }
    }

    override fun onDataDownloaded(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        binding.progressIndicator.hide()
       if(adapter is LibraryRecyclerAdapter || adapter is WeekRecyclerAdapter) createGridLayout(adapter)
       else binding.recyclerView.adapter= adapter
    }

    override fun onError(errorMsg: String) {
        binding.progressIndicator.hide()
        binding.errorText.text= errorMsg
    }

    override fun onScheduleItemClicked(dayModel: ScheduleDayModel) {
        fragmentManager?.let { ScheduleDayFragment.newInstance(it, dayModel) }
    }

}
