package com.smart.resources.schools_app.features.exam

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.utils.hide
import com.smart.resources.schools_app.core.utils.show
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.profile.AccountManager
import com.smart.resources.schools_app.sharedUi.SectionActivity



class ExamFragment : Fragment(), ExamRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: ExamViewModel
    private lateinit var adapter: ExamRecyclerAdapter

    companion object {
        fun newInstance(fm: FragmentManager) {

            val fragment =
                ExamFragment()

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
            adapter=  ExamRecyclerAdapter(this@ExamFragment)
            recyclerView.adapter =adapter
            lifecycleOwner= this@ExamFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.exams)
        setHasOptionsMenu(true)


        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!)
            .get(ExamViewModel::class.java).apply {
                    binding.listState= listState
                    exams.observe(this@ExamFragment, Observer { onExamsDownload(it) })
                }
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (AccountManager.instance?.getCurrentUser()?.userType == 1) {
            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuItem -> fragmentManager?.let { AddExamFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun onExamsDownload(result: List<ExamModel>) {
        adapter.submitList(result)
    }

    override fun onItemClick(examModel: ExamModel) {
        fragmentManager?.let { examModel.idExam?.let { it1 -> AddMarkFragment.newInstance(it, it1) } }
    }

}

