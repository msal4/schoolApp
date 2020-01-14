package com.smart.resources.schools_app.features.exam

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.utils.hide
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity



class ExamFragment : Fragment(), ExamRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: ExamViewModel



    companion object {
        var fragm:FragmentManager? = null
        fun newInstance(fm: FragmentManager) {

            val fragment =
                ExamFragment()

            fragm=fm
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
        (activity as SectionActivity).setCustomTitle(R.string.exams)
        setHasOptionsMenu(true)


        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(ExamViewModel::class.java).apply {
                if (SharedPrefHelper.instance?.userType == UserType.STUDENT) {
                    fetchExams() // TODO: improve performance by not fetching new data every time

                    getExams().observe(this@ExamFragment, Observer { onExamsDownload(it) })
                } else if (SharedPrefHelper.instance?.userType == UserType.TEACHER) {
                    fetchTeacherExams() // TODO: improve performance by not fetching new data every time

                    getTeacherExams().observe(this@ExamFragment, Observer { onExamsDownload(it) })
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (SharedPrefHelper.instance?.userType == UserType.TEACHER) {
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


    private fun onExamsDownload(result: MyResult<List<ExamModel>>) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_exams)
                else {
                    binding.recyclerView.adapter = ExamRecyclerAdapter(result.data,
                        this)
                }

            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

        binding.errorText.text = errorMsg
        binding.progressIndicator.hide()
    }

    override fun onItemClick(examModel: ExamModel) {

        fragm?.let { examModel.idExam?.let { it1 -> AddMarkFragment.newInstance(it, it1) } }
    }

}

