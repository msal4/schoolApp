package com.smart.resources.schools_app.features.homework

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

class HomeworkFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: HomeworkViewModel

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                HomeworkFragment()

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
        (activity as SectionActivity).setCustomTitle(R.string.homework)
        setHasOptionsMenu(true)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(HomeworkViewModel::class.java).apply {
                getExams().observe(this@HomeworkFragment, Observer{onHomeworkDownload(it)})
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(SharedPrefHelper.instance?.userType == UserType.TEACHER) {

            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMenuItem-> fragmentManager?.let { AddHomeworkFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }


    private  fun onHomeworkDownload(result: HomeworkResult) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_homework)
                else {
                    binding.recyclerView.adapter= HomeworkRecyclerAdapter(result.data)
                }

            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

        binding.errorText.text = errorMsg
        binding.progressIndicator.hide()
    }
}
